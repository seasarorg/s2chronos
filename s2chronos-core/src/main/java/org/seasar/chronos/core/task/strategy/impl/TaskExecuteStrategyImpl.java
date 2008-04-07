package org.seasar.chronos.core.task.strategy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.delegate.AsyncResult;
import org.seasar.chronos.core.delegate.MethodInvoker;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.task.TaskType;
import org.seasar.chronos.core.task.Transition;
import org.seasar.chronos.core.task.handler.TaskExecuteHandler;
import org.seasar.chronos.core.task.handler.impl.TaskGroupMethodExecuteHandlerImpl;
import org.seasar.chronos.core.task.handler.impl.TaskMethodExecuteHandlerImpl;
import org.seasar.chronos.core.task.impl.TaskMethodManager;
import org.seasar.chronos.core.task.impl.TaskMethodMetaData;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.core.util.ObjectUtil;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.SerializeUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskExecuteStrategyImpl implements TaskExecuteStrategy {

	private static final long serialVersionUID = -6091926808483360988L;

	private static Logger log = Logger.getLogger(TaskExecuteStrategyImpl.class);

	private static final String PROPERTY_NAME_RESCHEDULE = "reSchedule";

	private static final String PROPERTY_NAME_THREAD_POOL_SIZE = "threadPoolSize";

	private static final String PROPERTY_NAME_EXECUTED = "executed";

	private static final String PROPERTY_NAME_THREAD_POOL_TYPE = "threadPoolType";

	private static final String PROPERTY_NAME_START_TASK = "startTask";

	private static final String PROPERTY_NAME_END_TASK = "endTask";

	private static final String PROPERTY_NAME_SHUTDOWN_TASK = "shutdownTask";

	private static final String PROPERTY_NAME_TRIGGER = "trigger";

	private static final String METHOD_PREFIX_NAME_DO = "do";

	private static final String METHOD_NAME_INITIALIZE = "initialize";

	private static final String METHOD_NAME_DESTROY = "destroy";

	private static final ThreadPoolType DEFAULT_THREADPOOL_TYPE = ThreadPoolType.CACHED;

	private static final int DEFAULT_THREAD_POOLSIZE = 1;

	private static final String PROPERTY_NAME_THREADPOOL = "threadPool";

	private static final String PROPERTY_NAME_TASKNAME = "taskName";

	private static final String PROPERTY_NAME_TASKID = "taskId";

	private static final String PROPERTY_NAME_DESCRIPTION = "description";

	private static ConcurrentHashMap<TaskThreadPool, ExecutorService> threadPoolExecutorServiceMap = new ConcurrentHashMap<TaskThreadPool, ExecutorService>();

	private Object task;

	private Class<?> taskClass;

	private BeanDesc beanDesc;

	private MethodInvoker taskMethodInvoker;

	private MethodInvoker lifecycleMethodInvoker;

	private TaskMethodManager taskMethodManager;

	private TaskExecuteHandler taskMethodExecuteHandler;

	private TaskExecuteHandler taskGroupMethodExecuteHandler;

	private Object getterSignal;

	private Scheduler scheduler;

	private long taskId = 0;

	public TaskExecuteStrategyImpl() {

	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.taskMethodInvoker.awaitInvokes(time, timeUnit);
	}

	public boolean cancel() {
		this.taskMethodInvoker.cancelInvokes();
		return true;
	}

	public boolean checkMoveAnotherTask(final String nextTaskName) {
		TaskScheduleEntryManager tcsm = TaskScheduleEntryManager.getInstance();
		Object result = tcsm
				.forEach(new TaskScheduleEntryManager.TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry taskScheduleEntry) {
						Class<?> clazz = taskScheduleEntry.getTaskClass();
						Task task = clazz.getAnnotation(Task.class);
						if (task == null) {
							return null;
						}
						String taskName = task.name();
						if (nextTaskName.equals(taskName)) {
							return new Object();
						}
						return null;
					}
				});
		if (result != null) {
			return true;
		}
		return false;
	}

	private ExecutorService createJobMethodExecutorService(Object target) {
		ExecutorService result = null;
		ThreadPoolType type = this.getThreadPoolType(target);
		if (type == ThreadPoolType.FIXED) {
			int threadSize = this.getThreadPoolSize(target);
			result = Executors.newFixedThreadPool(threadSize);
		} else if (type == ThreadPoolType.CACHED) {
			result = Executors.newCachedThreadPool();
		} else if (type == ThreadPoolType.SINGLE) {
			result = Executors.newSingleThreadExecutor();
		} else if (type == ThreadPoolType.SCHEDULED) {
			int threadSize = this.getThreadPoolSize(target);
			result = Executors.newScheduledThreadPool(threadSize);
		}
		return result;
	}

	protected TaskExecuteHandler createTaskGroupMethodExecuteHandler(
			TaskExecuteHandler taskMethdoExecuteHandler) {
		TaskGroupMethodExecuteHandlerImpl result = new TaskGroupMethodExecuteHandlerImpl();
		result.setTaskMethodExecuteHandler(this.taskMethodExecuteHandler);
		return result;
	}

	protected TaskExecuteHandler createTaskMethodExecuteHandler() {
		return new TaskMethodExecuteHandlerImpl();
	}

	public String destroy() throws InterruptedException {
		String nextTask = null;
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_DESTROY)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_DESTROY);
			this.lifecycleMethodInvoker.endInvoke(ar);
			TaskMethodMetaData md = new TaskMethodMetaData(this.beanDesc,
					METHOD_NAME_DESTROY);
			nextTask = md.getNextTask();
		}
		this.setExecute(false);
		this.notifyGetterSignal();
		return nextTask;
	}

	public void execute(String startTaskName) throws InterruptedException {
		TaskType type = this.isGroupMethod(startTaskName) ? TaskType.JOBGROUP
				: TaskType.JOB;
		String nextTaskName = startTaskName;
		while (true) {
			TaskExecuteHandler teh = this.getTaskExecuteHandler(type);
			Transition transition = this.handleRequest(teh, nextTaskName);
			this.notifyGetterSignal();
			if (transition.isProcessResult()) {
				break;
			}
			type = type == TaskType.JOB ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
		}
	}

	private ExecutorService getCacheExecutorsService(
			TaskThreadPool taskThreadPool) {
		ExecutorService executorService = threadPoolExecutorServiceMap
				.get(taskThreadPool);
		if (executorService == null) {
			executorService = this
					.createJobMethodExecutorService(taskThreadPool);
			threadPoolExecutorServiceMap.put(taskThreadPool, executorService);
		}
		return executorService;
	}

	public boolean isReSchedule() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_RESCHEDULE)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_RESCHEDULE);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public String getDescription() {
		String result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_DESCRIPTION)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_DESCRIPTION);
			result = (String) pd.getValue(this.task);
		}
		return result;
	}

	public boolean isEndTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_END_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_END_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	private ExecutorService getExecutorService() {
		TaskThreadPool taskThreadPool = this.getThreadPool();
		ExecutorService jobMethodExecutorService = null;
		if (taskThreadPool == null) {
			jobMethodExecutorService = this
					.createJobMethodExecutorService(this.task);
		} else {
			jobMethodExecutorService = this
					.getCacheExecutorsService(taskThreadPool);
		}
		return jobMethodExecutorService;
	}

	public Scheduler getScheduler() {
		return this.scheduler;
	}

	public boolean isShutdownTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public boolean isStartTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_START_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_START_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public Object getTask() {
		return this.task;
	}

	public Class<?> getTaskClass() {
		return this.taskClass;
	}

	private TaskExecuteHandler getTaskExecuteHandler(TaskType type) {
		return type == TaskType.JOB ? this.taskMethodExecuteHandler
				: this.taskGroupMethodExecuteHandler;
	}

	public long getTaskId() {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TASKID)) {
			Long result = 0L;
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TASKID);
			result = (Long) pd.getValue(this.task);
			return result;
		}
		if (this.taskId == 0) {
			this.taskId = ObjectUtil.generateObjectId();
		}
		return this.taskId;
	}

	public String getTaskName() {
		String result = null;
		if (this.beanDesc != null) {
			if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TASKNAME)) {
				PropertyDesc pd = this.beanDesc
						.getPropertyDesc(PROPERTY_NAME_TASKNAME);
				result = (String) pd.getValue(this.task);
			}
		}
		return result;
	}

	public TaskThreadPool getThreadPool() {
		TaskThreadPool result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREADPOOL)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREADPOOL);
			result = (TaskThreadPool) pd.getValue(this.task);
		}
		return result;
	}

	public int getThreadPoolSize() {
		return this.getThreadPoolSize(this.task);
	}

	private int getThreadPoolSize(Object target) {
		Integer result = DEFAULT_THREAD_POOLSIZE;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREAD_POOL_SIZE)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREAD_POOL_SIZE);
			result = (Integer) pd.getValue(target);
		}
		return result;
	}

	public ThreadPoolType getThreadPoolType() {
		return this.getThreadPoolType(this.task);
	}

	private ThreadPoolType getThreadPoolType(Object target) {
		ThreadPoolType type = DEFAULT_THREADPOOL_TYPE;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREAD_POOL_TYPE)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREAD_POOL_TYPE);
			type = (ThreadPoolType) pd.getValue(target);
		}
		return type;
	}

	public TaskTrigger getTrigger() {
		TaskTrigger result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TRIGGER);
			result = (TaskTrigger) pd.getValue(this.task);
		} else {
			return this.taskTrigger;
		}

		return result;
	}

	private Transition handleRequest(TaskExecuteHandler taskExecuteHandler,
			String startTaskName) throws InterruptedException {
		taskExecuteHandler.setTaskExecuteStrategy(this);
		taskExecuteHandler.setMethodInvoker(this.taskMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.taskMethodManager);
		return taskExecuteHandler.handleRequest(startTaskName);
	}

	public String initialize() throws InterruptedException {
		this.setExecute(true);
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_INITIALIZE)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_INITIALIZE);
			this.lifecycleMethodInvoker.endInvoke(ar);
			TaskMethodMetaData md = new TaskMethodMetaData(this.beanDesc,
					METHOD_NAME_INITIALIZE);
			this.notifyGetterSignal();
			return md.getNextTask();
		}
		return null;
	}

	public boolean isExecute() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_EXECUTED)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_EXECUTED);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	private boolean isGroupMethod(String groupName) {
		return this.taskMethodManager.existGroup(groupName);
	}

	public void load() {
		FileInputStream fis = null;
		try {
			File targetFile = new File("C:\\temp\\", this.getTaskClass()
					.getCanonicalName());
			if (targetFile.exists()) {
				fis = new FileInputStream(targetFile);
				int size = fis.read();
				byte[] objectArray = new byte[size];
				fis.read(objectArray);
				this.task = SerializeUtil.fromBinaryToObject(objectArray);
			}
		} catch (IOException e) {
			throw new IORuntimeException(e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}
			}
		}

		Map<String, Object> taskProperties = new HashMap<String, Object>();
		int size = this.beanDesc.getPropertyDescSize();
		for (int i = 0; i < size; i++) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc(i);
			Object value = pd.getValue(this.task);
			taskProperties.put(pd.getPropertyName(), value);
		}
	}

	private void notifyGetterSignal() {
		synchronized (this.getterSignal) {
			this.getterSignal.notify();
		}
	}

	public void prepare() {

		ReflectionUtil.forNameNoException(this.componentDef.getComponentClass()
				.getName());
		if (HotdeployUtil.isHotdeploy()) {
			HotdeployUtil.start();
		}

		this.task = this.componentDef.getComponent();
		this.taskClass = this.componentDef.getComponentClass();

		this.taskMethodExecuteHandler = this.createTaskMethodExecuteHandler();
		this.taskGroupMethodExecuteHandler = this
				.createTaskGroupMethodExecuteHandler(this.taskMethodExecuteHandler);

		this.taskMethodExecuteHandler.setTaskExecuteStrategy(this);
		this.taskGroupMethodExecuteHandler.setTaskExecuteStrategy(this);

		this.beanDesc = BeanDescFactory.getBeanDesc(this.taskClass);
		this.taskMethodManager = new TaskMethodManager(this.taskClass,
				METHOD_PREFIX_NAME_DO);

		ExecutorService lifecycleMethodExecutorService = Executors
				.newSingleThreadExecutor();
		ExecutorService jobMethodExecutorService = this.getExecutorService();
		this.taskMethodInvoker = new MethodInvoker(jobMethodExecutorService,
				this.task, this.beanDesc);
		this.lifecycleMethodInvoker = new MethodInvoker(
				lifecycleMethodExecutorService, this.task, this.beanDesc);

		this.save();
		this.load();
	}

	public void unprepare() {

		this.taskMethodInvoker = null;
		this.lifecycleMethodInvoker = null;

		if (HotdeployUtil.isHotdeploy()) {
			HotdeployUtil.stop();
		}
	}

	public void save() {
		// Map<String, Object> taskProperties = new HashMap<String, Object>();
		// int size = beanDesc.getPropertyDescSize();
		// for (int i = 0; i < size; i++) {
		// PropertyDesc pd = beanDesc.getPropertyDesc(i);
		// Object value = pd.getValue(this.task);
		// taskProperties.put(pd.getPropertyName(), value);
		// }
		// if (size > 0) {
		// byte[] binary = SerializeUtil.fromObjectToBinary(taskProperties);
		// FileOutputStream fos = null;
		// try {
		// File targetFile = new File("C:\\temp\\", this.getTaskClass()
		// .getCanonicalName());
		// fos = new FileOutputStream(targetFile);
		// fos.write(binary.length);
		// fos.write(binary);
		// } catch (IOException e) {
		// throw new IORuntimeException(e);
		// } finally {
		// if (fos != null) {
		// try {
		// fos.close();
		// } catch (IOException e) {
		// throw new IORuntimeException(e);
		// }
		// }
		// }
		// }
	}

	public void setEndTask(boolean endTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_END_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_END_TASK);
			pd.setValue(this.task, endTask);
		}
	}

	public void setExecute(boolean executed) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_EXECUTED)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_EXECUTED);
			pd.setValue(this.task, executed);
		}
	}

	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void setShutdownTask(boolean shutdownTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK);
			pd.setValue(this.task, shutdownTask);
		}
	}

	public void setStartTask(boolean startTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_START_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_START_TASK);
			pd.setValue(this.task, startTask);
		}
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setTaskClass(Class<?> taskClass) {
		this.taskClass = taskClass;
	}

	public void setTaskId(long taskId) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TASKID)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TASKID);
			pd.setValue(this.task, taskId);
			return;
		}
		this.taskId = taskId;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setThreadPool(TaskThreadPool taskThreadPool) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREADPOOL)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREADPOOL);
			pd.setValue(this.task, taskThreadPool);
		}
	}

	private TaskTrigger taskTrigger;

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(TaskTrigger taskTrigger) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TRIGGER);
			pd.setValue(this.task, taskTrigger);
		} else {
			// プロパティを持っていなければセットする。
			this.taskTrigger = taskTrigger;
		}
	}

	public void waitOne() throws InterruptedException {
		this.taskMethodInvoker.waitInvokes();
	}

	private ComponentDef componentDef;

	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
	}

}

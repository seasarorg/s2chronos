package org.seasar.chronos.task.strategy.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskContenaStateManager;
import org.seasar.chronos.store.task.TaskStore;
import org.seasar.chronos.task.TaskType;
import org.seasar.chronos.task.Transition;
import org.seasar.chronos.task.handler.TaskExecuteHandler;
import org.seasar.chronos.task.handler.impl.TaskGroupMethodExecuteHandlerImpl;
import org.seasar.chronos.task.handler.impl.TaskMethodExecuteHandlerImpl;
import org.seasar.chronos.task.impl.TaskMethodManager;
import org.seasar.chronos.task.impl.TaskMethodMetaData;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;
import org.seasar.framework.log.Logger;

public class TaskExecuteStrategyImpl implements TaskExecuteStrategy {

	private static final long serialVersionUID = -6091926808483360988L;

	private static Logger log = Logger.getLogger(TaskExecuteStrategyImpl.class);

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

	private Object task;

	private Class taskClass;

	private BeanDesc beanDesc;

	private MethodInvoker taskMethodInvoker;

	private MethodInvoker lifecycleMethodInvoker;

	private TaskMethodManager taskMethodManager;

	private TaskExecuteHandler taskMethodExecuteHandler;

	private TaskExecuteHandler taskGroupMethodExecuteHandler;

	private Object getterSignal;

	private TaskStore store;

	public TaskExecuteStrategyImpl() {

	}

	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler getScheduler() {
		return this.scheduler;
	}

	protected TaskExecuteHandler createTaskGroupMethodExecuteHandler() {
		return new TaskGroupMethodExecuteHandlerImpl();
	}

	protected TaskExecuteHandler createTaskMethodExecuteHandler() {
		return new TaskMethodExecuteHandlerImpl();
	}

	private boolean isGroupMethod(String groupName) {
		return this.taskMethodManager.existGroup(groupName);
	}

	public void setTaskClass(Class taskClass) {
		this.taskClass = taskClass;
	}

	public Class getTaskClass() {
		return this.taskClass;
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public Object getTask() {
		return this.task;
	}

	public void prepare() {

		this.taskMethodExecuteHandler = this.createTaskMethodExecuteHandler();
		this.taskGroupMethodExecuteHandler = this
				.createTaskGroupMethodExecuteHandler();

		this.beanDesc = BeanDescFactory.getBeanDesc(this.taskClass);
		this.taskMethodManager = new TaskMethodManager(taskClass,
				METHOD_PREFIX_NAME_DO);

		ExecutorService lifecycleMethodExecutorService = Executors
				.newSingleThreadExecutor();
		ExecutorService jobMethodExecutorService = getExecutorService();
		this.taskMethodInvoker = new MethodInvoker(jobMethodExecutorService,
				this.task, this.beanDesc);
		this.lifecycleMethodInvoker = new MethodInvoker(
				lifecycleMethodExecutorService, this.task, this.beanDesc);
	}

	private ExecutorService getExecutorService() {
		TaskThreadPool taskThreadPool = this.getThreadPool();
		ExecutorService jobMethodExecutorService = null;
		if (taskThreadPool == null) {
			jobMethodExecutorService = createJobMethodExecutorService(this.task);
		} else {
			jobMethodExecutorService = getCacheExecutorsService(taskThreadPool);
		}
		return jobMethodExecutorService;
	}

	private static ConcurrentHashMap<TaskThreadPool, ExecutorService> threadPoolExecutorServiceMap = new ConcurrentHashMap<TaskThreadPool, ExecutorService>();

	private ExecutorService getCacheExecutorsService(
			TaskThreadPool taskThreadPool) {
		ExecutorService executorService = threadPoolExecutorServiceMap
				.get(taskThreadPool);
		if (executorService == null) {
			executorService = createJobMethodExecutorService(taskThreadPool);
			threadPoolExecutorServiceMap.put(taskThreadPool, executorService);
		}
		return executorService;
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

	private void notifyGetterSignal() {
		synchronized (this.getterSignal) {
			this.getterSignal.notify();
		}
	}

	private Transition handleRequest(TaskExecuteHandler taskExecuteHandler,
			String startTaskName) throws InterruptedException {
		taskExecuteHandler.setTaskExecuteStrategy(this);
		taskExecuteHandler.setMethodInvoker(this.taskMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.taskMethodManager);
		return taskExecuteHandler.handleRequest(startTaskName);
	}

	private TaskExecuteHandler getTaskExecuteHandler(TaskType type) {
		return type == TaskType.JOB ? this.taskMethodExecuteHandler
				: this.taskGroupMethodExecuteHandler;
	}

	public void execute(String startTaskName) throws InterruptedException {
		TaskType type = isGroupMethod(startTaskName) ? TaskType.JOBGROUP
				: TaskType.JOB;
		String nextTaskName = startTaskName;
		while (true) {
			log.debug("while begin");
			TaskExecuteHandler teh = getTaskExecuteHandler(type);
			Transition transition = handleRequest(teh, nextTaskName);
			this.notifyGetterSignal();
			if (transition.isProcessResult()) {
				log.debug("while break");
				break;
			}
			type = (type == TaskType.JOB) ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
			log.debug("while end");
		}
	}

	public boolean cancel() {
		this.taskMethodInvoker.cancelInvokes();
		return true;
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.taskMethodInvoker.awaitInvokes(time, timeUnit);
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
		this.taskMethodInvoker = null;
		this.lifecycleMethodInvoker = null;
		HotdeployUtil.stop();
		return nextTask;
	}

	private ExecutorService createJobMethodExecutorService(Object target) {
		ExecutorService result = null;
		ThreadPoolType type = getThreadPoolType(target);
		if (type == ThreadPoolType.FIXED) {
			int threadSize = getThreadPoolSize(target);
			result = Executors.newFixedThreadPool(threadSize);
		} else if (type == ThreadPoolType.CACHED) {
			result = Executors.newCachedThreadPool();
		} else if (type == ThreadPoolType.SINGLE) {
			result = Executors.newSingleThreadExecutor();
		} else if (type == ThreadPoolType.SCHEDULED) {
			int threadSize = getThreadPoolSize(target);
			result = Executors.newScheduledThreadPool(threadSize);
		}
		return result;
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

	public int getThreadPoolSize() {
		return getThreadPoolSize(this.task);
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

	public ThreadPoolType getThreadPoolType() {
		return getThreadPoolType(this.task);
	}

	public void setExecute(boolean executed) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_EXECUTED)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_EXECUTED);
			pd.setValue(this.task, executed);
		}
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

	public boolean getStartTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_START_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_START_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public void setStartTask(boolean startTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_START_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_START_TASK);
			pd.setValue(this.task, startTask);
		}
	}

	public boolean getEndTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_END_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_END_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public void setEndTask(boolean endTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_END_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_END_TASK);
			pd.setValue(this.task, endTask);
		}
	}

	public boolean getShutdownTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK);
			result = (Boolean) pd.getValue(this.task);
		}
		return result;
	}

	public void setShutdownTask(boolean shutdownTask) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_SHUTDOWN_TASK);
			pd.setValue(this.task, shutdownTask);
		}
	}

	public TaskTrigger getTrigger() {
		TaskTrigger result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TRIGGER);
			result = (TaskTrigger) pd.getValue(this.task);
		}
		return result;
	}

	// @Binding(bindingType = BindingType.NONE)
	// public void setTrigger(TaskTrigger taskTrigger) {
	// if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
	// PropertyDesc pd = this.beanDesc
	// .getPropertyDesc(PROPERTY_NAME_TRIGGER);
	// pd.setValue(this.task, taskTrigger);
	// }
	// }

	public TaskThreadPool getThreadPool() {
		TaskThreadPool result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREADPOOL)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREADPOOL);
			result = (TaskThreadPool) pd.getValue(this.task);
		}
		return result;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setThreadPool(TaskThreadPool taskThreadPool) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREADPOOL)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREADPOOL);
			pd.setValue(this.task, taskThreadPool);
		}
	}

	public void waitOne() throws InterruptedException {
		this.taskMethodInvoker.waitInvokes();
	}

	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

	public boolean checkMoveAnotherTask(final String nextTaskName) {
		TaskContenaStateManager tcsm = TaskContenaStateManager.getInstance();
		Object result = tcsm
				.forEach(new TaskContenaStateManager.TaskContenaHanlder() {
					public Object processTaskContena(TaskContena taskContena) {
						Class<?> clazz = taskContena.getTaskClass();
						Task task = (Task) clazz.getAnnotation(Task.class);
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

	public String getTaskName() {
		String result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TASKNAME)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TASKNAME);
			result = (String) pd.getValue(this.task);
		}
		return result;
	}

	public int getTaskId() {
		int result = 0;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TASKID)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TASKID);
			result = (Integer) pd.getValue(this.task);
		} else {
			result = this.task.hashCode();
		}
		return result;
	}

	public void load() {
		this.store.loadFromStore(this.getTaskId(), this);
	}

	public void save() {
		this.store.saveToStore(this);
	}
}

package org.seasar.chronos.task.strategy.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.task.TaskType;
import org.seasar.chronos.task.Transition;
import org.seasar.chronos.task.handler.TaskExecuteHandler;
import org.seasar.chronos.task.impl.TaskMethodManager;
import org.seasar.chronos.task.impl.TaskMethodMetaData;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.trigger.Trigger;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.container.hotdeploy.HotdeployUtil;

public class TaskExecuteStrategyImpl implements TaskExecuteStrategy {

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

	private Object task;

	private Class taskClass;

	private ComponentDef taskComponentDef;

	private BeanDesc beanDesc;

	private MethodInvoker taskMethodInvoker;

	private MethodInvoker lifecycleMethodInvoker;

	private TaskMethodManager taskMethodManager;

	private TaskExecuteHandler taskMethodExecuteHandler;

	private TaskExecuteHandler taskGroupMethodExecuteHandler;

	private Object getterSignal;

	public TaskExecuteStrategyImpl() {

	}

	public void setTaskGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler) {
		this.taskGroupMethodExecuteHandler = jobGroupMethdoExecuteHandler;
	}

	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler) {
		this.taskMethodExecuteHandler = jobMethdoExecuteHandler;
	}

	private boolean isGroupMethod(String groupName) {
		return this.taskMethodManager.existGroup(groupName);
	}

	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.taskComponentDef = taskComponentDef;

	}

	public void prepare() {

		this.task = this.taskComponentDef.getComponent();
		this.taskClass = this.taskComponentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.taskClass);
		this.taskMethodManager = new TaskMethodManager(taskClass,
				METHOD_PREFIX_NAME_DO);

		ExecutorService lifecycleMethodExecutorService = Executors
				.newSingleThreadExecutor();
		ExecutorService jobMethodExecutorService = getJobMethodExecutorService();

		this.taskMethodInvoker = new MethodInvoker(jobMethodExecutorService,
				this.task, this.beanDesc);
		this.lifecycleMethodInvoker = new MethodInvoker(
				lifecycleMethodExecutorService, this.task, this.beanDesc);
	}

	public String initialize() throws InterruptedException {
		try {
			String className = taskComponentDef.getComponentClass().getName();
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩ catch ÉuÉçÉbÉN
			e.printStackTrace();
		}
		HotdeployUtil.start();
		this.setExecuted(true);
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
			TaskExecuteHandler teh = getTaskExecuteHandler(type);
			Transition transition = handleRequest(teh, nextTaskName);
			this.notifyGetterSignal();
			if (transition.isProcessResult()) {
				break;
			}
			type = (type == TaskType.JOB) ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
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

	public void destroy() throws InterruptedException {
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_DESTROY)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_DESTROY);
			this.lifecycleMethodInvoker.endInvoke(ar);
		}
		this.setExecuted(false);
		this.notifyGetterSignal();
		this.taskMethodInvoker = null;
		this.lifecycleMethodInvoker = null;
		HotdeployUtil.stop();
	}

	private ExecutorService getJobMethodExecutorService() {
		ExecutorService result = null;
		ThreadPoolType type = getThreadPoolType();

		if (type == ThreadPoolType.FIXED) {
			int threadSize = getThreadPoolSize();
			result = Executors.newFixedThreadPool(threadSize);
		} else if (type == ThreadPoolType.CACHED) {
			result = Executors.newCachedThreadPool();
		} else if (type == ThreadPoolType.SINGLE) {
			result = Executors.newSingleThreadExecutor();
		} else if (type == ThreadPoolType.SCHEDULED) {
			int threadSize = getThreadPoolSize();
			result = Executors.newScheduledThreadPool(threadSize);
		}
		return result;
	}

	public int getThreadPoolSize() {
		Integer result = DEFAULT_THREAD_POOLSIZE;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREAD_POOL_SIZE)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREAD_POOL_SIZE);
			result = (Integer) pd.getValue(this.task);
		}
		return result;
	}

	public ThreadPoolType getThreadPoolType() {
		ThreadPoolType type = DEFAULT_THREADPOOL_TYPE;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_THREAD_POOL_TYPE)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_THREAD_POOL_TYPE);
			type = (ThreadPoolType) pd.getValue(this.task);
		}
		return type;
	}

	public void setExecuted(boolean executed) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_EXECUTED)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_EXECUTED);
			pd.setValue(this.task, executed);
		}
	}

	public boolean isExecuted() {
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

	public Trigger getTrigger() {
		Trigger result = null;
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TRIGGER);
			result = (Trigger) pd.getValue(this.task);
		}
		return result;
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(Trigger trigger) {
		if (this.beanDesc.hasPropertyDesc(PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(PROPERTY_NAME_TRIGGER);
			pd.setValue(this.task, trigger);
		}
	}

	public void waitOne() throws InterruptedException {
		this.taskMethodInvoker.waitInvokes();
	}

	public void setGetterSignal(Object getterSignal) {
		this.getterSignal = getterSignal;
	}

}

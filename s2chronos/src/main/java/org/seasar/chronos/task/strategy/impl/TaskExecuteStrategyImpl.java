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
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class TaskExecuteStrategyImpl implements TaskExecuteStrategy {

	private static final String METHOD_PREFIX_NAME_DO = "do";

	private static final String METHOD_NAME_INITIALIZE = "initialize";

	private static final String METHOD_NAME_DESTROY = "destroy";

	private static final ThreadPoolType DEFAULT_THREADPOOL_TYPE = ThreadPoolType.CACHED;

	private Object job;

	private Class jobClass;

	private ComponentDef jobComponentDef;

	private BeanDesc beanDesc;

	private MethodInvoker jobMethodInvoker;

	private MethodInvoker lifecycleMethodInvoker;

	private TaskMethodManager taskMethodManager;

	private TaskExecuteHandler jobMethodExecuteHandler;

	private TaskExecuteHandler jobGroupMethodExecuteHandler;

	public TaskExecuteStrategyImpl() {

	}

	private boolean isGroupMethod(String groupName) {
		return this.taskMethodManager.existGroup(groupName);
	}

	private void prepareMethodInvoker() {
		ExecutorService lifecycleMethodExecutorService = Executors
				.newSingleThreadExecutor();
		ExecutorService jobMethodExecutorService = getJobMethodExecutorService();

		this.jobMethodInvoker = new MethodInvoker(jobMethodExecutorService,
				this.job, this.beanDesc);
		this.lifecycleMethodInvoker = new MethodInvoker(
				lifecycleMethodExecutorService, this.job, this.beanDesc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#initialize(org.seasar.framework.container.ComponentDef)
	 */
	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {

		this.jobComponentDef = jobComponentDef;
		this.job = this.jobComponentDef.getComponent();
		this.jobClass = this.jobComponentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.jobClass);
		this.taskMethodManager = new TaskMethodManager(jobClass,
				METHOD_PREFIX_NAME_DO);

		this.prepareMethodInvoker();

		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_INITIALIZE)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_INITIALIZE);
			this.lifecycleMethodInvoker.endInvoke(ar);
			TaskMethodMetaData md = new TaskMethodMetaData(this.beanDesc,
					METHOD_NAME_INITIALIZE);
			return md.getNextTask();
		}

		return null;
	}

	private Transition handleRequest(TaskExecuteHandler taskExecuteHandler,
			String startTaskName) throws InterruptedException {
		taskExecuteHandler.setTaskExecuteStrategy(this);
		taskExecuteHandler.setMethodInvoker(this.jobMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.taskMethodManager);
		return taskExecuteHandler.handleRequest(startTaskName);
	}

	private TaskExecuteHandler getTaskExecuteHandler(TaskType type) {
		return type == TaskType.JOB ? this.jobMethodExecuteHandler
				: this.jobGroupMethodExecuteHandler;
	}

	public void execute(String startJobName) throws InterruptedException {
		this.setExecuted(true);
		TaskType type = isGroupMethod(startJobName) ? TaskType.JOBGROUP
				: TaskType.JOB;
		String nextTaskName = startJobName;
		while (true) {
			TaskExecuteHandler teh = getTaskExecuteHandler(type);
			Transition transition = handleRequest(teh, nextTaskName);
			if (transition.isProcessResult()) {
				break;
			}
			type = (type == TaskType.JOB) ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
		}
		this.setExecuted(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#destroy()
	 */
	public void destroy() throws InterruptedException {
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_DESTROY)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_DESTROY);
			this.lifecycleMethodInvoker.endInvoke(ar);
		}
		this.jobMethodInvoker = null;
		this.lifecycleMethodInvoker = null;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#getThreadPoolSize()
	 */
	public int getThreadPoolSize() {
		Integer result = 1;
		if (this.beanDesc.hasPropertyDesc("threadPoolSize")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("threadPoolSize");
			result = (Integer) pd.getValue(this.job);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#getThreadPoolType()
	 */
	public ThreadPoolType getThreadPoolType() {
		ThreadPoolType type = DEFAULT_THREADPOOL_TYPE;
		if (this.beanDesc.hasPropertyDesc("threadPoolType")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("threadPoolType");
			type = (ThreadPoolType) pd.getValue(this.job);
		}
		return type;
	}

	public void setExecuted(boolean executed) {
		if (this.beanDesc.hasPropertyDesc("executed")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("executed");
			pd.setValue(this.job, executed);
		}
	}

	public boolean isExecuted() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc("executed")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("executed");
			result = (Boolean) pd.getValue(this.job);
		}
		return result;
	}

	public boolean getStartTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc("startTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("startTask");
			result = (Boolean) pd.getValue(this.job);
		}
		return result;
	}

	public void setStartTask(boolean startTask) {
		if (this.beanDesc.hasPropertyDesc("startTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("startTask");
			pd.setValue(this.job, startTask);
		}
	}

	public boolean getEndTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc("endTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("endTask");
			result = (Boolean) pd.getValue(this.job);
		}
		return result;
	}

	public void setEndTask(boolean endTask) {
		if (this.beanDesc.hasPropertyDesc("endTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("endTask");
			pd.setValue(this.job, endTask);
		}
	}

	public boolean getShutdownTask() {
		Boolean result = false;
		if (this.beanDesc.hasPropertyDesc("shutdownTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("shutdownTask");
			result = (Boolean) pd.getValue(this.job);
		}
		return result;
	}

	public void setShutdownTask(boolean shutdownTask) {
		if (this.beanDesc.hasPropertyDesc("shutdownTask")) {
			PropertyDesc pd = this.beanDesc.getPropertyDesc("shutdownTask");
			pd.setValue(this.job, shutdownTask);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#setJobGroupMethodExecuteHandler(org.seasar.chronos.task.TaskExecuteHandler)
	 */
	public void setTaskGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler) {
		this.jobGroupMethodExecuteHandler = jobGroupMethdoExecuteHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.task.impl.JobExecuteStrategy#setJobMethodExecuteHandler(org.seasar.chronos.task.TaskExecuteHandler)
	 */
	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler) {
		this.jobMethodExecuteHandler = jobMethdoExecuteHandler;
	}

	public void cancel() {
		this.setShutdownTask(true);
		this.jobMethodInvoker.cancelInvokes();
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.jobMethodInvoker.awaitInvokes(time, timeUnit);
	}

}

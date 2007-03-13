package org.seasar.chronos.job.strategy.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.TaskType;
import org.seasar.chronos.job.Transition;
import org.seasar.chronos.job.handler.TaskExecuteHandler;
import org.seasar.chronos.job.impl.JobMethodMetaData;
import org.seasar.chronos.job.impl.MethodGroupMap;
import org.seasar.chronos.job.strategy.JobExecuteStrategy;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class JobExecuteStrategyImpl implements JobExecuteStrategy {

	private static final String METHOD_NAME_INITIALIZE = "initialize";

	private static final String METHOD_NAME_DESTROY = "destroy";

	private static final String METHOD_NAME_CANEXECUTE = "canExecute";

	private static final boolean DEFAULT_CANEXECUTE = true;

	private static final String METHOD_PREFIX_NAME_DO = "do";

	private Object job;

	private Class jobClass;

	private ComponentDef jobComponentDef;

	private BeanDesc beanDesc;

	private MethodInvoker jobMethodInvoker;

	private MethodInvoker lifecycleMethodInvoker;

	private MethodGroupMap methodGroupMap;

	private TaskExecuteHandler jobMethodExecuteHandler;

	private TaskExecuteHandler jobGroupMethodExecuteHandler;

	public JobExecuteStrategyImpl() {

	}

	private boolean isGroupMethod(String groupName) {
		return this.methodGroupMap.existGroup(groupName);
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
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#initialize(org.seasar.framework.container.ComponentDef)
	 */
	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {

		this.jobComponentDef = jobComponentDef;
		this.job = this.jobComponentDef.getComponent();
		this.jobClass = this.jobComponentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.jobClass);
		this.methodGroupMap = new MethodGroupMap(jobClass,
				METHOD_PREFIX_NAME_DO);

		this.prepareMethodInvoker();

		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_INITIALIZE)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_INITIALIZE);
			this.lifecycleMethodInvoker.endInvoke(ar);
			JobMethodMetaData md = new JobMethodMetaData(this.beanDesc,
					METHOD_NAME_INITIALIZE);
			return md.getNextTask();
		}

		return null;
	}

	private Transition handleRequest(TaskExecuteHandler taskExecuteHandler,
			String startJobName) throws InterruptedException {
		taskExecuteHandler.setMethodInvoker(this.jobMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.methodGroupMap);
		return taskExecuteHandler.handleRequest(startJobName);
	}

	private TaskExecuteHandler getTaskExecuteHandler(TaskType type) {
		return type == TaskType.JOB ? this.jobMethodExecuteHandler
				: this.jobGroupMethodExecuteHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#callJob(java.lang.String)
	 */
	public void callJob(String startJobName) throws InterruptedException {
		TaskType type = isGroupMethod(startJobName) ? TaskType.JOBGROUP
				: TaskType.JOB;
		String nextTaskName = startJobName;
		while (true) {
			TaskExecuteHandler teh = getTaskExecuteHandler(type);
			Transition transition = handleRequest(teh, nextTaskName);
			if (transition.isProcessResult()) {
				break;
			}
			type = type == TaskType.JOB ? TaskType.JOBGROUP : TaskType.JOB;
			nextTaskName = transition.getNextTaskName();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#destroy()
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#canExecute()
	 */
	public boolean canExecute() throws InterruptedException {
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_CANEXECUTE)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_CANEXECUTE);
			this.lifecycleMethodInvoker.endInvoke(ar);
		}
		return DEFAULT_CANEXECUTE;
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
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#getThreadPoolSize()
	 */
	public int getThreadPoolSize() {
		Integer result = 1;
		if (this.beanDesc.hasPropertyDesc("threadPoolSize")) {
			PropertyDesc threadPoolSize = this.beanDesc
					.getPropertyDesc("threadPoolSize");
			result = (Integer) threadPoolSize.getValue(this.job);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#getThreadPoolType()
	 */
	public ThreadPoolType getThreadPoolType() {
		ThreadPoolType type = ThreadPoolType.CACHED;
		if (this.beanDesc.hasPropertyDesc("threadPoolType")) {
			PropertyDesc threadPoolType = this.beanDesc
					.getPropertyDesc("threadPoolType");
			type = (ThreadPoolType) threadPoolType.getValue(this.job);
		}
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#setJobGroupMethodExecuteHandler(org.seasar.chronos.job.TaskExecuteHandler)
	 */
	public void setJobGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler) {
		this.jobGroupMethodExecuteHandler = jobGroupMethdoExecuteHandler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.job.impl.JobExecuteStrategy#setJobMethodExecuteHandler(org.seasar.chronos.job.TaskExecuteHandler)
	 */
	public void setJobMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler) {
		this.jobMethodExecuteHandler = jobMethdoExecuteHandler;
	}

	public void cancel() {
		this.jobMethodInvoker.getExecutorService().shutdownNow();
	}

}

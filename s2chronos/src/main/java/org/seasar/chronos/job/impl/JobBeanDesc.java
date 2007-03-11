package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.delegate.AsyncResult;
import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.TaskExecuteHandler;
import org.seasar.chronos.job.TaskExecuteHandlerFactory;
import org.seasar.chronos.job.TaskType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class JobBeanDesc {

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

	public JobBeanDesc(ComponentDef jobComponentDef) {
		this.jobComponentDef = jobComponentDef;
		this.job = this.jobComponentDef.getComponent();
		this.jobClass = this.jobComponentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.jobClass);
		this.methodGroupMap = new MethodGroupMap(jobClass,
				METHOD_PREFIX_NAME_DO);
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

	public String initialize() throws Throwable {

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

	public void callJob(String startJobName) throws Throwable {

		TaskType type = isGroupMethod(startJobName) ? TaskType.JOBGROUP
				: TaskType.JOB;

		TaskExecuteHandler taskExecuteHandler = TaskExecuteHandlerFactory
				.create(type);

		taskExecuteHandler.setMethodInvoker(this.jobMethodInvoker);
		taskExecuteHandler.setMethodGroupMap(this.methodGroupMap);

		taskExecuteHandler.handleRequest(startJobName);
	}

	public void destroy() throws Throwable {
		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_DESTROY)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_DESTROY);
			this.lifecycleMethodInvoker.endInvoke(ar);
		}
		this.jobMethodInvoker = null;
		this.lifecycleMethodInvoker = null;
	}

	public boolean canExecute() throws Throwable {
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

	public int getThreadPoolSize() {
		PropertyDesc threadPoolSize = this.beanDesc
				.getPropertyDesc("threadPoolSize");
		Integer result = (Integer) threadPoolSize.getValue(this.job);
		return result;
	}

	public ThreadPoolType getThreadPoolType() {
		PropertyDesc threadPoolType = this.beanDesc
				.getPropertyDesc("threadPoolType");
		ThreadPoolType type = (ThreadPoolType) threadPoolType
				.getValue(this.job);
		return type;
	}

}

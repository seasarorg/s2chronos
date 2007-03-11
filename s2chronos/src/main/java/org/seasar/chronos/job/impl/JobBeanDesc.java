package org.seasar.chronos.job.impl;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.concurrent.AsyncResult;
import org.seasar.chronos.concurrent.BeanDescInvoker;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class JobBeanDesc {

	private static final String METHOD_NAME_INITIALIZE = "initialize";

	private Object job;

	private Class jobClass;

	private ComponentDef jobComponentDef;

	private BeanDesc beanDesc;

	private BeanDescInvoker jobMethodInvoker;

	private BeanDescInvoker lifecycleMethodInvoker;

	public JobBeanDesc(ComponentDef jobComponentDef) {
		this.jobComponentDef = jobComponentDef;
		this.job = this.jobComponentDef.getComponent();
		this.jobClass = this.jobComponentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.jobClass);
	}

	public String initialize() throws Throwable {

		ExecutorService lifecycleMethodExecutorService = Executors
				.newSingleThreadExecutor();

		ExecutorService jobMethodExecutorService = getJobMethodExecutorService();

		this.jobMethodInvoker = new BeanDescInvoker(jobMethodExecutorService,
				this.job, this.beanDesc);

		this.lifecycleMethodInvoker = new BeanDescInvoker(
				lifecycleMethodExecutorService, this.job, this.beanDesc);

		if (this.lifecycleMethodInvoker.hasMethod(METHOD_NAME_INITIALIZE)) {
			AsyncResult ar = this.lifecycleMethodInvoker
					.beginInvoke(METHOD_NAME_INITIALIZE);
			this.lifecycleMethodInvoker.endInvoke(ar);

			Method m = this.beanDesc.getMethod(METHOD_NAME_INITIALIZE);
			JobMethodMetaData md = new JobMethodMetaData(m);
			return md.getNextTask();
		}

		return null;
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

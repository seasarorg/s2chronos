package org.seasar.chronos.job.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.job.method.Clone;
import org.seasar.chronos.annotation.job.method.Join;
import org.seasar.chronos.annotation.job.method.Next;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class JobBeanDesc {

	private static final boolean WAIT_DEFAULT = false;

	private Object job;

	private BeanDesc beanDesc;

	private ExecutorService executoerService;

	public JobBeanDesc(ComponentDef jobComponentDef) {

	}

	public void initialize() {

	}

	public void destroy() {

	}

	public JobResult invoke(String methodName)
			throws MethodNotFoundRuntimeException {
		String next = null;
		long cloneSize = 1;
		boolean wait = WAIT_DEFAULT;
		Method method = this.beanDesc.getMethod(methodName);
		Join joinAnnotation = method.getAnnotation(Join.class);
		if (joinAnnotation != null) {
			wait = (joinAnnotation.value() == JoinType.Wait);
		}
		Next nextAnnotation = method.getAnnotation(Next.class);
		if (nextAnnotation != null) {
			next = nextAnnotation.value();
		}
		Clone clone = method.getAnnotation(Clone.class);
		if (clone != null) {
			cloneSize = clone.value();
		}
		List<Object> resultList = new ArrayList<Object>();
		for (int i = 0; i < cloneSize; i++) {
			Object result = (Object) beanDesc
					.invoke(this.job, methodName, null);
			resultList.add(result);
		}
		JobResult jobResult = new JobResult(resultList, next, wait);
		return jobResult;
	}

	/**
	 * listener public void doJobACallback(AsyncResult ar){ }
	 * 
	 * jobBeanDesc.beginInvoke("doJobA", new Callback(listener), null );
	 * 
	 * @param methodName
	 * @param callback
	 * @param state
	 * @return
	 */
	public AsyncResult beginInvoke(final String methodName,
			final Callback callback, final Object state) {
		Future<JobResult> future = executoerService
				.submit(new Callable<JobResult>() {
					public JobResult call() throws Exception {
						BeanDesc beanDesc = BeanDescFactory
								.getBeanDesc(callback.getTarget().getClass());

						JobResult jobResult = invoke(methodName);

						String callbackMethodName = callback.getMethodName();
						if (callbackMethodName == null) {
							callbackMethodName = methodName + "Callback";
						}
						beanDesc
								.invoke(callback.getTarget(),
										callbackMethodName,
										new Object[] { new AsyncResult(
												jobResult, state) });
						return jobResult;
					}
				});
		return new AsyncResult(future, state);
	}

	public JobResult endInvoke(AsyncResult asyncResult) throws Throwable {
		try {
			return asyncResult.getJobResult();
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}

	public int getThreadPoolSize() {
		return 0;
	}

	public ThreadPoolType getThreadPoolType() {
		PropertyDesc threadPoolType = this.beanDesc
				.getPropertyDesc("threadPoolType");
		ThreadPoolType type = (ThreadPoolType) threadPoolType.getValue(job);
		return type;
	}

}

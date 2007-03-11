package org.seasar.chronos.delegate;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class MethodInvoker {

	private static final String CALLBACK_SUFFIX = "Callback";

	private Class targetClass;

	private Object target;

	private BeanDesc beanDesc;

	private ExecutorService executorService;

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public MethodInvoker(ExecutorService executorService, Object target,
			BeanDesc beanDesc) {
		this.executorService = executorService;
		this.beanDesc = beanDesc;
		this.target = target;
		this.targetClass = this.beanDesc.getBeanClass();
	}

	public MethodInvoker(ExecutorService executorService,
			ComponentDef componentDef) {
		this.executorService = executorService;
		this.target = componentDef.getComponent();
		this.targetClass = componentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.targetClass);
	}

	public boolean hasMethod(String methodName) {
		return this.beanDesc.hasMethod(methodName);
	}

	public Method getMethod(String methodName) {
		return this.beanDesc.getMethod(methodName);
	}

	public Object invoke(String methodName) {
		return invoke(methodName, null);
	}

	public Object invoke(String methodName, Object[] args)
			throws MethodNotFoundRuntimeException {
		Object result = (Object) this.beanDesc.invoke(this.target, methodName,
				args);
		return result;
	}

	public AsyncResult beginInvoke(final String methodName) {
		return beginInvoke(methodName, null, null, null);
	}

	public AsyncResult beginInvoke(final String methodName,
			final MethodCallback methodCallback, final Object state) {
		return beginInvoke(methodName, null, methodCallback, state);
	}

	public AsyncResult beginInvoke(final String methodName,
			final Object[] args, final MethodCallback methodCallback,
			final Object state) {

		final AsyncResult asyncResult = new AsyncResult();

		Future<Object> future = this.executorService
				.submit(new Callable<Object>() {
					public Object call() throws Exception {
						synchronized (asyncResult) {
							asyncResult.wait();
						}
						Object result = invoke(methodName, args);
						callbackHandler(methodName, methodCallback, asyncResult);
						return result;
					}

					private void callbackHandler(final String methodName,
							final MethodCallback methodCallback,
							final AsyncResult asyncResult) {

						BeanDesc beanDesc = BeanDescFactory
								.getBeanDesc(methodCallback.getTarget()
										.getClass());
						StringBuffer callbackMethodName = new StringBuffer(
								methodCallback.getMethodName());
						if (callbackMethodName == null) {
							callbackMethodName.append(methodName);
							callbackMethodName.append(CALLBACK_SUFFIX);
						}
						beanDesc.invoke(methodCallback.getTarget(),
								callbackMethodName.toString(),
								new Object[] { asyncResult });
					}
				});

		synchronized (asyncResult) {
			asyncResult.setFuture(future);
			asyncResult.setState(state);
			asyncResult.notify();
		}
		return asyncResult;
	}

	public Object endInvoke(AsyncResult asyncResult) throws Throwable {
		try {
			return asyncResult.getFuture().get();
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}

	public boolean cancelInvoke(AsyncResult asyncResult) {
		return asyncResult.getFuture().cancel(true);
	}

	public boolean cancelInvoke(AsyncResult asyncResult, boolean shutdown) {
		return asyncResult.getFuture().cancel(shutdown);
	}

}

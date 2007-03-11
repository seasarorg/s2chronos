package org.seasar.chronos.delegate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.MethodNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;

public class MethodInvoker {

	private Class targetClass;

	private Object target;

	private BeanDesc beanDesc;

	private ExecutorService executorService;

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

	public Object invoke(String methodName) {
		return invoke(methodName, null);
	}

	public Object invoke(String methodName, Object[] args)
			throws MethodNotFoundRuntimeException {
		Object result = (Object) beanDesc.invoke(this.target, methodName, args);
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
			final Object[] args, final MethodCallback methodCallback, final Object state) {

		final AsyncResult asyncResult = new AsyncResult();

		Future<Object> future = executorService.submit(new Callable<Object>() {
			public Object call() throws Exception {
				synchronized (asyncResult) {
					asyncResult.wait();
				}

				BeanDesc beanDesc = BeanDescFactory.getBeanDesc(methodCallback
						.getTarget().getClass());

				Object result = invoke(methodName, args);

				StringBuffer callbackMethodName = new StringBuffer(methodCallback
						.getMethodName());
				if (callbackMethodName == null) {
					callbackMethodName.append(methodName);
					callbackMethodName.append("Callback");
				}
				beanDesc.invoke(methodCallback.getTarget(), callbackMethodName
						.toString(), new Object[] { asyncResult });
				return result;
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

}

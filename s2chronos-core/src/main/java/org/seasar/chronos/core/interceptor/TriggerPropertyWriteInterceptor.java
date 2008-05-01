package org.seasar.chronos.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.TaskPropertyHandlerFactory;

public class TriggerPropertyWriteInterceptor implements MethodInterceptor {

	private TaskPropertyHandlerFactory taskPropertyHandlerFactory;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyHandler cmd = taskPropertyHandlerFactory
				.create(methodInvocation.getMethod());
		if (cmd != null) {
			return cmd.execute(methodInvocation);
		}
		return methodInvocation.proceed();
	}

	public void setTaskPropertyHandlerFactory(
			TaskPropertyHandlerFactory taskPropertyHandlerFactory) {
		this.taskPropertyHandlerFactory = taskPropertyHandlerFactory;
	}

}

package org.seasar.chronos.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.TaskPropertyReadHandlerFactory;

public class TriggerPropertyReadInterceptor implements MethodInterceptor {

	private TaskPropertyReadHandlerFactory taskPropertyReadCommandFactory;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyHandler cmd = taskPropertyReadCommandFactory
				.create(methodInvocation.getMethod());
		if (cmd != null) {
			return cmd.execute(methodInvocation);
		}
		return methodInvocation.proceed();
	}

	public void setTaskPropertyReadCommandFactory(
			TaskPropertyReadHandlerFactory taskPropertyReadCommandFactory) {
		this.taskPropertyReadCommandFactory = taskPropertyReadCommandFactory;
	}

}

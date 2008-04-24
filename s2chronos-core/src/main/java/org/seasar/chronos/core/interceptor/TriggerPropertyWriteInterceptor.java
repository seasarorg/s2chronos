package org.seasar.chronos.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.TaskPropertyWriteHandlerFactory;

public class TriggerPropertyWriteInterceptor implements MethodInterceptor {

	private TaskPropertyWriteHandlerFactory taskPropertyWriteCommandFactory;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyHandler cmd = taskPropertyWriteCommandFactory
				.create(methodInvocation.getMethod());
		if (cmd != null) {
			return cmd.execute(methodInvocation);
		}
		return methodInvocation.proceed();
	}

	public void setTaskPropertyWriteCommandFactory(
			TaskPropertyWriteHandlerFactory taskPropertyWriteCommandFactory) {
		this.taskPropertyWriteCommandFactory = taskPropertyWriteCommandFactory;
	}

}
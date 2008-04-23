package org.seasar.chronos.core.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskPropertyReadCommandFactory;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;

public class TriggerPropertyReadInterceptor implements MethodInterceptor {

	private TaskPropertyReadCommandFactory taskPropertyReadCommandFactory;

	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyHandler cmd = taskPropertyReadCommandFactory
				.create(methodInvocation.getMethod());
		if (cmd != null) {
			return cmd.execute(methodInvocation);
		}
		return methodInvocation.proceed();
	}

	public void setTaskPropertyReadCommandFactory(
			TaskPropertyReadCommandFactory taskPropertyReadCommandFactory) {
		this.taskPropertyReadCommandFactory = taskPropertyReadCommandFactory;
	}

}

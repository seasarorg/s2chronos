package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;

public class TaskSetReSchedulePropertyWriteCommand extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		return methodInvocation.proceed();
	}

}

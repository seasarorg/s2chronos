package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;

public class TaskSetTaskNamePropertyWriteCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		return methodInvocation.proceed();
	}

}

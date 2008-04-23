package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;

public class TaskSetTaskNamePropertyWriteCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		return methodInvocation.proceed();
	}

}

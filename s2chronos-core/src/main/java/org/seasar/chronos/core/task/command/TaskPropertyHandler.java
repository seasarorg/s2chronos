package org.seasar.chronos.core.task.command;

import org.aopalliance.intercept.MethodInvocation;

public interface TaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable;

}

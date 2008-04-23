package org.seasar.chronos.core.task.handler;

import org.aopalliance.intercept.MethodInvocation;

public interface TaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable;

}

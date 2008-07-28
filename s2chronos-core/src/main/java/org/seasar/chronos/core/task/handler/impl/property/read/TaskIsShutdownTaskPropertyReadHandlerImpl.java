package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskIsShutdownTaskPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean shutdown = false;
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger != null) {
			shutdown = taskTrigger.isShutdownTask();
		} else {
			shutdown = (Boolean) methodInvocation.proceed();
		}
		return shutdown;
	}

}

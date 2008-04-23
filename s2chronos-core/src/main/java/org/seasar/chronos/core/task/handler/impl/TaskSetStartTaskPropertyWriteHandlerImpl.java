package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;

public class TaskSetStartTaskPropertyWriteHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger == null) {
			methodInvocation.proceed();
		} else {
			taskTrigger
					.setStartTask((Boolean) methodInvocation.getArguments()[0]);
		}
		return null;
	}

}

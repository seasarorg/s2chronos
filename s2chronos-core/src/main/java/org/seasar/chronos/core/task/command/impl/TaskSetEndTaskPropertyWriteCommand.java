package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;

public class TaskSetEndTaskPropertyWriteCommand extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger == null) {
			methodInvocation.proceed();
		} else {
			taskTrigger
					.setEndTask((Boolean) methodInvocation.getArguments()[0]);
		}
		return null;
	}
}

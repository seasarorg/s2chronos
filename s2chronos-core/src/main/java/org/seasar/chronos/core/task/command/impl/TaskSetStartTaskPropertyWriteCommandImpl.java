package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;

public class TaskSetStartTaskPropertyWriteCommandImpl extends
		AbstractTaskPropertyCommand {

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

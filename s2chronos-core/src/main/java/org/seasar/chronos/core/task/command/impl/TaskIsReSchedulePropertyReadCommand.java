package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;

public class TaskIsReSchedulePropertyReadCommand extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean reSchedule = (Boolean) methodInvocation.proceed();
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger != null) {
			reSchedule = reSchedule || taskTrigger.isReSchedule();
		}
		return reSchedule;
	}

}

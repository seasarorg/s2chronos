package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;

public class TaskIsReSchedulePropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

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

package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskPropertyReader;

public class TaskIsEndTaskPropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		Boolean end = false;
		TaskPropertyReader tpr = this.getTaskPropertyReader(methodInvocation);
		TaskTrigger taskTrigger = tpr.getTrigger(null);
		if (taskTrigger == null) {
			end = (Boolean) methodInvocation.proceed();
		} else {
			end = taskTrigger.isEndTask();
		}
		return end;
	}

}

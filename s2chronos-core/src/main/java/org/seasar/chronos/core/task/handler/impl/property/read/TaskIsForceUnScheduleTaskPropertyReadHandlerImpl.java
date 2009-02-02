package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskIsForceUnScheduleTaskPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean forceUnScheduleTask = (Boolean) methodInvocation.proceed();
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (taskTrigger != null) {
			forceUnScheduleTask = forceUnScheduleTask
					|| taskTrigger.isForceUnScheduleTask();
		}
		return forceUnScheduleTask;
	}

}

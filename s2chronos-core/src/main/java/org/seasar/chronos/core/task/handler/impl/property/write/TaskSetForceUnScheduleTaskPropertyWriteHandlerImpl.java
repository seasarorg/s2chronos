package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskSetForceUnScheduleTaskPropertyWriteHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		TaskPropertyReader taskPropertyReader = this.getTaskPropertyWriter(
				methodInvocation).getTaskPropertyReader();
		TaskTrigger trigger = taskPropertyReader.getTrigger(null);
		if (trigger != null) {
			trigger.setForceUnScheduleTask((Boolean) methodInvocation
					.getArguments()[0]);
		}
		return null;
	}
}

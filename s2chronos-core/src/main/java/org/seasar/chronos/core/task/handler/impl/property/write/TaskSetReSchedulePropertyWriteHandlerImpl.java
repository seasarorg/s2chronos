package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskSetReSchedulePropertyWriteHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		this.getTaskPropertyReader(methodInvocation).loadTask(
				methodInvocation.getThis(), methodInvocation.getClass());
		TaskTrigger trigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (trigger != null) {
			trigger.setReSchedule((Boolean) methodInvocation.getArguments()[0]);
			return null;
		}
		return methodInvocation.proceed();
	}

}

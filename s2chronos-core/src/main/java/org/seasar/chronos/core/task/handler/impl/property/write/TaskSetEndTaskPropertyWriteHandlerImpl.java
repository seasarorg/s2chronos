package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyWriteHandler;

public class TaskSetEndTaskPropertyWriteHandlerImpl extends
		AbstractTaskPropertyWriteHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		this.getTaskPropertyReader(methodInvocation).loadTask(
				methodInvocation.getThis(), methodInvocation.getClass());
		TaskTrigger trigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (trigger != null) {
			trigger.setEndTask((Boolean) methodInvocation.getArguments()[0]);
			return null;
		}
		return methodInvocation.proceed();
	}
}

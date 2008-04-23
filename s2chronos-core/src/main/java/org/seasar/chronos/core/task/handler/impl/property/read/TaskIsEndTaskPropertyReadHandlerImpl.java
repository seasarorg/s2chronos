package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskIsEndTaskPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

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

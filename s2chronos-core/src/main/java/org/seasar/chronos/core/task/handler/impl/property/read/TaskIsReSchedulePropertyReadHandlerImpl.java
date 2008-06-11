package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;
import org.seasar.framework.log.Logger;

public class TaskIsReSchedulePropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	private static final Logger log = Logger.getLogger(TaskIsReSchedulePropertyReadHandlerImpl.class);
	
	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean reSchedule = (Boolean) methodInvocation.proceed();
		TaskTrigger taskTrigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		log.debug("Class = "+taskTrigger+" reSchedule = "+reSchedule);
		if (taskTrigger != null) {
			boolean triggerReSchedule = (Boolean)taskTrigger.isReSchedule();
			log.debug("Class = "+taskTrigger+" triggerReSchedule = "+triggerReSchedule);
			reSchedule = reSchedule || triggerReSchedule;
		}
		return reSchedule;
	}

}

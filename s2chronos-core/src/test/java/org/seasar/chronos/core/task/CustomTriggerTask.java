package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.framework.log.Logger;

@Task
@CronTrigger(expression = "*/20 * * * * ?")
public class CustomTriggerTask {

	private Logger log = Logger.getLogger(CustomTriggerTask.class);

	public TimedTask timedTask;

	@NextTask("taskA")
	public void initialize() {
		log.debug("[CustomTriggerTask::initialize]");
	}

	public void doTaskA() {
		log.debug("[CustomTriggerTask::doTaskA] = " + timedTask);
	}

	public void destroy() {
		log.debug("[CustomTriggerTask::destroy]");
	}

}

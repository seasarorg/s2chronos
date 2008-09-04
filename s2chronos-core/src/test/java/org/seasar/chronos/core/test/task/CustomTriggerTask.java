package org.seasar.chronos.core.test.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.framework.log.Logger;

@Task
@NonDelayTrigger
public class CustomTriggerTask {

	private final Logger log = Logger.getLogger(CustomTriggerTask.class);

	@NextTask("taskA")
	public void start() {
		log.debug("[CustomTriggerTask::start]");
	}

	public void doTaskA() {
		log.debug("[CustomTriggerTask::doTaskA]");
	}

	public void end() {
		log.debug("[CustomTriggerTask::end]");
	}

}

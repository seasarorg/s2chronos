package org.seasar.chronos.core.test.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.framework.log.Logger;

@Task
@NonDelayTrigger
public class ExecuteMethodTask {

	private final Logger log = Logger.getLogger(ExecuteMethodTask.class);

	public void execute() {
		log.debug("[ExecuteMethodTask::execute]");
	}

}

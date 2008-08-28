package org.seasar.chronos.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.framework.log.Logger;

@Task
@NonDelayTrigger
public class SimpleTask {

	private static Logger log = Logger.getLogger(SimpleTask.class);

	public void doExecute() {
		log.info("[[SimpleTask::doExecute]]");
	}
}

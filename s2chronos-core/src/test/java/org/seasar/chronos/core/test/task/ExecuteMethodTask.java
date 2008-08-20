package org.seasar.chronos.core.test.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.framework.log.Logger;

@Task
@NonDelayTrigger
public class ExecuteMethodTask {

	private final Logger log = Logger.getLogger(ExecuteMethodTask.class);

	private static int count = 1;

	@CloneTask(10)
	public void doExecute() {
		log.debug("[ExecuteMethodTask::doExecute:" + count++ + "]");
	}
}

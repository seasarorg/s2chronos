package org.seasar.chronos.core.task;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.trigger.CronTrigger;
import org.seasar.framework.log.Logger;

@Task
public class TestTask {

	private static Logger log = Logger.getLogger(TestTask.class);
	private TaskTrigger trigger = new CronTrigger("*/1 * * * *");

	public TaskTrigger getTrigger() {
		return trigger;
	}

	public void initialize() {
		log
				.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SimpleTask::initialize");
	}

}

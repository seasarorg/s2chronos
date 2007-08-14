package org.seasar.chronos.core.task;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.trigger.CronTrigger;
import org.seasar.framework.log.Logger;

@Task(name = "example")
public class SimpleCronTask {

	private static Logger log = Logger.getLogger(SimpleCronTask.class);

	private CronTrigger cronTrigger = new CronTrigger();

	public SimpleCronTask() {
		cronTrigger.setCronExpression("21 * * * *");
	}

	public TaskTrigger getTrigger() {
		return cronTrigger;
	}

	// タスクが実行されるときに最初に呼ばれる
	public synchronized void initialize() {
		log.info("======================SimpleCronTask::initialize");
	}

}

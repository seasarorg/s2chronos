package org.seasar.chronos.core.task;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.trigger.CronTrigger;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.framework.log.Logger;

@Task(autoSchedule = true)
@Component(instance = InstanceType.SINGLETON)
public class TestTask {

	private static Logger log = Logger.getLogger(TestTask.class);

	private TaskTrigger trigger = new CronTrigger("*/1 * * * *");

	public TaskTrigger getTrigger() {
		return trigger;
	}

	public boolean isStartTask() {
		return true;
	}

	public void initialize() {
		log.info("SimpleTask::initialize");
	}

}

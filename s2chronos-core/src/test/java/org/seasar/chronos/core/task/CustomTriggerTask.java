package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.CustomTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.framework.log.Logger;

@Task
@CustomTrigger
public class CustomTriggerTask {

	private Logger log = Logger.getLogger(CustomTriggerTask.class);

	public void initialize() {
		log.debug("[CustomTriggerTask::initialize]");
	}

	public void destroy() {
		log.debug("[CustomTriggerTask::destroy]");
	}

}

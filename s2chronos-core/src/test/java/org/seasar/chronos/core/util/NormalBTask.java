package org.seasar.chronos.core.util;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.model.TaskTrigger;

@Task
public class NormalBTask {

	public TaskTrigger getTrigger() {
		return null;
	}

}

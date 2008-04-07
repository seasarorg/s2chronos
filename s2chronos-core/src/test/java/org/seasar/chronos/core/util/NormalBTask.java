package org.seasar.chronos.core.util;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;

@Task
public class NormalBTask {

	public TaskTrigger getTrigger() {
		return null;
	}

}

package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;

public class TriggerWrapper extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private TaskTrigger taskTrigger;

	public TriggerWrapper(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public boolean isEndTask() {
		return taskTrigger.isEndTask();
	}

	public boolean isStartTask() {
		return taskTrigger.isStartTask();
	}

	public void setEndTask(Boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(Boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

}

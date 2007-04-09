package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;

public class TriggerDecorator extends AbstractTrigger {

	private TaskTrigger taskTrigger;

	public TriggerDecorator(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public Boolean getEndTask() {
		return taskTrigger.getEndTask();
	}

	public Boolean getStartTask() {
		return taskTrigger.getStartTask();
	}

	public void setEndTask(Boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(Boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

}

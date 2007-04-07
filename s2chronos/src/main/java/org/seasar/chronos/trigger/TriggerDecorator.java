package org.seasar.chronos.trigger;

import org.seasar.chronos.TaskTrigger;

public class TriggerDecorator extends AbstractTrigger {

	private TaskTrigger taskTrigger;

	public TriggerDecorator(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public boolean getEndTask() {
		return taskTrigger.getEndTask();
	}

	public boolean getStartTask() {
		return taskTrigger.getStartTask();
	}

	public void setEndTask(boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

}

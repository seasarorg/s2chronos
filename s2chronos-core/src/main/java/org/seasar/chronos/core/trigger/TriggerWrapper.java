package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;

public class TriggerWrapper extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private TaskTrigger taskTrigger;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public TriggerWrapper(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public boolean isEndTask() {
		return taskTrigger.isEndTask();
	}

	public boolean isStartTask() {
		return taskTrigger.isStartTask();
	}

	public void setEndTask(boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

}

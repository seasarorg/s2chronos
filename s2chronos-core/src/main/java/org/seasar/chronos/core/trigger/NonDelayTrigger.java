package org.seasar.chronos.core.trigger;

public class NonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	public NonDelayTrigger() {
		super("nonDelayTrigger");
	}

	@Override
	public boolean isReSchedule() {
		return false;
	}

	public boolean isStartTask() {
		return true;
	}

	public boolean isEndTask() {
		return false;
	}

	public void setStartTask(Boolean startTask) {

	}

	public void setEndTask(Boolean endTask) {

	}

}

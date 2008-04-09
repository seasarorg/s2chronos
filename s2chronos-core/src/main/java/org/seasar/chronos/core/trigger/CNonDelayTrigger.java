package org.seasar.chronos.core.trigger;

public class CNonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	public CNonDelayTrigger() {
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

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

}

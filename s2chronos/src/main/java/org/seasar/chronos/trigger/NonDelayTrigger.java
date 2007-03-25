package org.seasar.chronos.trigger;

public class NonDelayTrigger extends AbstractTrigger {

	public boolean getStartTask() {
		return true;
	}

	public boolean getEndTask() {
		return false;
	}

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

}

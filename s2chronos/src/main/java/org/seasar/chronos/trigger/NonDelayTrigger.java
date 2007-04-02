package org.seasar.chronos.trigger;

public class NonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 9118006820441048598L;

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

package org.seasar.chronos.core.trigger;

public class NonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 9118006820441048598L;

	public NonDelayTrigger() {
		super("nonDelayTrigger");
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

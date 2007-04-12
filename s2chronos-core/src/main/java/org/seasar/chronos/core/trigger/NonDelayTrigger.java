package org.seasar.chronos.core.trigger;

public class NonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 9118006820441048598L;

	public Boolean getStartTask() {
		return true;
	}

	public Boolean getEndTask() {
		return false;
	}

	public void setStartTask(Boolean startTask) {

	}

	public void setEndTask(Boolean endTask) {

	}

}

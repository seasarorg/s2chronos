package org.seasar.chronos.core.trigger;

public class DelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = -3996902051005552696L;

	private long delay = 0;

	public void setDelay(long delay) {
		this.delay = System.currentTimeMillis() + delay;
	}

	public long getDelay() {
		return delay;
	}

	public Boolean getStartTask() {
		if (this.isExecute()) {
			return false;
		}

		boolean startTimeCheck = false;

		// 開始時刻の確認
		startTimeCheck = (System.currentTimeMillis() >= delay);

		return startTimeCheck;
	}

	public Boolean getEndTask() {
		return false;
	}

	public void setStartTask(Boolean startTask) {

	}

	public void setEndTask(Boolean endTask) {

	}

}

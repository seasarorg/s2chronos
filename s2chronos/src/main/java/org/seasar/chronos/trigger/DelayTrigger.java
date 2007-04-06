package org.seasar.chronos.trigger;

import org.seasar.chronos.store.DelayTriggerStore;

public class DelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = -3996902051005552696L;

	private DelayTriggerStore store;

	private long delay = 0;

	public void setDelay(long delay) {
		this.delay = System.currentTimeMillis() + delay;
	}

	public long getDelay() {
		return delay;
	}

	public boolean getStartTask() {
		if (this.isExecuted()) {
			return false;
		}

		boolean startTimeCheck = false;

		// 開始時刻の確認
		startTimeCheck = (System.currentTimeMillis() >= delay);

		return startTimeCheck;
	}

	public boolean getEndTask() {
		return false;
	}

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

	public void setStore(DelayTriggerStore store) {
		this.store = store;
	}

	@Override
	public void load() {
		this.store.loadFromStore(this.getId(), this);
	}

	@Override
	public void save() {
		this.store.saveToStore(this);
	}
}

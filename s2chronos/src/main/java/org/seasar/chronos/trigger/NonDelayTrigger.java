package org.seasar.chronos.trigger;

import org.seasar.chronos.store.NonDelayTriggerStore;

public class NonDelayTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 9118006820441048598L;

	private NonDelayTriggerStore store;

	@Override
	public void save() {
		this.store.saveToStore(this);
	}

	@Override
	public void load() {
		this.store.loadFromStore(this.getId(), this);
	}

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

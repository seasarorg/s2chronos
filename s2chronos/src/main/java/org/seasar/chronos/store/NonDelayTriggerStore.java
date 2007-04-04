package org.seasar.chronos.store;

import org.seasar.chronos.trigger.NonDelayTrigger;

public interface NonDelayTriggerStore {

	public interface ProcessHandler {
		public Object process(NonDelayTrigger trigger);
	}

	public void saveToStore(NonDelayTrigger trigger);

	public NonDelayTrigger loadFromStore(long id);

}

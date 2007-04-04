package org.seasar.chronos.store;

import org.seasar.chronos.trigger.DelayTrigger;

public interface DelayTriggerStore {

	public interface ProcessHandler {
		public Object process(DelayTrigger trigger);
	}

	public void saveToStore(DelayTrigger trigger);

	public DelayTrigger loadFromStore(long id);

}

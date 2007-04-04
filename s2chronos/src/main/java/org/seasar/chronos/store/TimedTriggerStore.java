package org.seasar.chronos.store;

import org.seasar.chronos.store.annotation.Store;
import org.seasar.chronos.trigger.TimedTrigger;

@Store(bean = TimedTrigger.class)
public interface TimedTriggerStore {

	public void saveToStore(TimedTrigger trigger);

	public TimedTrigger loadFromStore(long id);

	public void loadFromStore(long id, TimedTrigger trigger);

}

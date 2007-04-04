package org.seasar.chronos.store;

import org.seasar.chronos.trigger.CronTrigger;

public interface CronTriggerStore {

	public interface ProcessHandler {
		public Object process(CronTrigger trigger);
	}

}

package org.seasar.chronos.store.trigger;

import org.seasar.chronos.store.dao.TriggerDao;

public abstract class AbstractTriggerStore {

	private TriggerDao triggerDao;

	public TriggerDao getTriggerDao() {
		return triggerDao;
	}

	public void setTriggerDao(TriggerDao triggerDao) {
		this.triggerDao = triggerDao;
	}

}

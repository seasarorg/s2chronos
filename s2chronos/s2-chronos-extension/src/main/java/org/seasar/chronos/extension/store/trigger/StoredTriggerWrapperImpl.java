package org.seasar.chronos.extension.store.trigger;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.trigger.TriggerWrapper;
import org.seasar.chronos.extension.store.TriggerStore;

public class StoredTriggerWrapperImpl extends TriggerWrapper {

	private TriggerStore triggerStore;

	public StoredTriggerWrapperImpl(TaskTrigger taskTrigger) {
		super(taskTrigger);
	}

	public void load() {
		triggerStore.loadFromStore(this.getId(), this);
	}

	public void save() {
		triggerStore.saveToStore(this);
	}

	public void setTriggerStore(TriggerStore triggerStore) {
		this.triggerStore = triggerStore;
	}
}

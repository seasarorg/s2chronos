package org.seasar.chronos.extension.store.trigger;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.trigger.TriggerDecorator;
import org.seasar.chronos.extension.store.TriggerStore;

public class StoredTriggerDecorator extends TriggerDecorator {

	private TriggerStore triggerStore;

	public StoredTriggerDecorator(TaskTrigger taskTrigger) {
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

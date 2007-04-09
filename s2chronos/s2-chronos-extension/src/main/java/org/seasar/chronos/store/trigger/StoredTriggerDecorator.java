package org.seasar.chronos.store.trigger;

import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.store.TriggerStore;
import org.seasar.chronos.trigger.TriggerDecorator;

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

package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.schedule.ScheduleEntryWrapper;
import org.seasar.chronos.extension.store.ScheduleStore;

public class StoredScheduleEntryWrapperImpl extends ScheduleEntryWrapper {

	private ScheduleStore scheduleStore;

	public StoredScheduleEntryWrapperImpl(TaskScheduleEntry taskScheduleEntry) {
		super(taskScheduleEntry);
	}

	@Override
	public void load() {
		scheduleStore.loadFromStoreByObjectId(this.getScheduleId(), this);
	}

	@Override
	public void save() {
		scheduleStore.saveToStore(this);
	}

	public void setScheduleStore(ScheduleStore scheduleStore) {
		this.scheduleStore = scheduleStore;
	}

}

package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.schedule.ScheduleEntryWrapper;
import org.seasar.chronos.extension.store.ScheduleStore;
import org.seasar.chronos.extension.store.TaskStore;

public class StoredScheduleEntryWrapperImpl extends ScheduleEntryWrapper {

	private ScheduleStore scheduleStore;

	private TaskStore taskStore;

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
		taskStore.saveToStore(this.getTaskExecutorService());
	}

	public void setScheduleStore(ScheduleStore scheduleStore) {
		this.scheduleStore = scheduleStore;
	}

	public void setTaskStore(TaskStore taskStore) {
		this.taskStore = taskStore;
	}

}

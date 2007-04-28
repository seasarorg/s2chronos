package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.TaskScheduleEntry;

public interface ScheduleStore {

	public void loadAllTasks();

	public void loadFromStore(Long id, TaskScheduleEntry taskScheduleEntry);

	public void loadFromStoreByObjectId(Long id,
			TaskScheduleEntry taskScheduleEntry);

	public void saveAllTasks();

	public Long saveToStore(TaskScheduleEntry taskScheduleEntry);

}

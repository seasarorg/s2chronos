package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.TaskTrigger;

public interface TriggerStore {

	public TaskTrigger loadFromStore(Long id);

	public void loadFromStore(Long id, TaskTrigger trigger);

	public Long saveToStore(TaskTrigger trigger);

}

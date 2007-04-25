package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.task.TaskProperties;

public interface TaskStore {

	public void loadFromStore(Long id, TaskProperties task);

	public void loadFromStoreByObjectId(Long objectId, TaskProperties task);

	public Long saveToStore(TaskProperties task);

}

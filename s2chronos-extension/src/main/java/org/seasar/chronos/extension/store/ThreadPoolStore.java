package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.TaskThreadPool;

public interface ThreadPoolStore {

	public TaskThreadPool loadFromStore(Long id);

	public void loadFromStore(Long id, TaskThreadPool threadPool);

	public TaskThreadPool loadFromStoreByObjectId(Long objectId);

	public Long saveToStore(TaskThreadPool threadPool);

}

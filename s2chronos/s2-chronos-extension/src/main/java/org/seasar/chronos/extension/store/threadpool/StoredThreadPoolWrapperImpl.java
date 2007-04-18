package org.seasar.chronos.extension.store.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.threadpool.ThreadPoolWrapper;
import org.seasar.chronos.extension.store.ThreadPoolStore;

public class StoredThreadPoolWrapperImpl extends ThreadPoolWrapper {

	private static final long serialVersionUID = -3020909396727360370L;

	private ThreadPoolStore threadPoolStore;

	public StoredThreadPoolWrapperImpl(TaskThreadPool taskThreadPool) {
		super(taskThreadPool);
	}

	@Override
	public void load() {
		threadPoolStore.loadFromStore(this.getId(), this);
	}

	@Override
	public void save() {
		threadPoolStore.saveToStore(this);
	}

	public void setThreadPoolStore(ThreadPoolStore threadPoolStore) {
		this.threadPoolStore = threadPoolStore;
	}

}
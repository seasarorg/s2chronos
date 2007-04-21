package org.seasar.chronos.extension.store.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.threadpool.ThreadPoolWrapper;
import org.seasar.chronos.extension.store.ThreadPoolStore;

public class StoredThreadPoolWrapperImpl extends ThreadPoolWrapper {

	private static final long serialVersionUID = -3020909396727360370L;

	private ThreadPoolStore threadPoolStoreImpl;

	public StoredThreadPoolWrapperImpl(TaskThreadPool taskThreadPool) {
		super(taskThreadPool);
	}

	@Override
	public void load() {
		// threadPoolStoreImpl.loadFromStore(this.getId(), this);
	}

	@Override
	public void save() {
		threadPoolStoreImpl.saveToStore(this);
	}

	public void setThreadPoolStore(ThreadPoolStore threadPoolStoreImpl) {
		this.threadPoolStoreImpl = threadPoolStoreImpl;
	}

}

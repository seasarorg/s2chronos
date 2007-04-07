package org.seasar.chronos.threadpool;

import org.seasar.chronos.Serializable;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.store.ThreadPoolStore;

public class ThreadPool implements TaskThreadPool, Serializable {

	private static final long serialVersionUID = 3092612895816238852L;

	private int id;

	private ThreadPoolType threadPoolType;

	private int threadPoolSize;

	private ThreadPoolStore threadPoolStore;

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public ThreadPoolType getThreadPoolType() {
		return threadPoolType;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public void load() {
		threadPoolStore.loadFromStore(this.getId(), this);
	}

	public void save() {
		threadPoolStore.saveToStore(this);
	}

	public int getId() {
		if (id == 0) {
			id = this.hashCode();
		}
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setThreadPoolStore(ThreadPoolStore threadPoolStore) {
		this.threadPoolStore = threadPoolStore;
	}

}

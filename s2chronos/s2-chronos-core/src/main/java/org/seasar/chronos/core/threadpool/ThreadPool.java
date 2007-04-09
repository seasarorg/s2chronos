package org.seasar.chronos.core.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;

public class ThreadPool implements TaskThreadPool {

	private static final long serialVersionUID = 3092612895816238852L;

	private Integer id;

	private ThreadPoolType threadPoolType;

	private Integer threadPoolSize;

	// private ThreadPoolStore threadPoolStore;

	public int getId() {
		if (id == null) {
			id = this.hashCode();
		}
		return id;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public ThreadPoolType getThreadPoolType() {
		return threadPoolType;
	}

	public void load() {
		// threadPoolStore.loadFromStore(this.getId(), this);
	}

	public void save() {
		// threadPoolStore.saveToStore(this);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	// public void setThreadPoolStore(ThreadPoolStore threadPoolStore) {
	// this.threadPoolStore = threadPoolStore;
	// }

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

}

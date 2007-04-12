package org.seasar.chronos.core.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;

public class ThreadPool implements TaskThreadPool {

	private static final long serialVersionUID = 3092612895816238852L;

	private Integer id;

	private ThreadPoolType threadPoolType;

	private Integer threadPoolSize;

	public Integer getId() {
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
	}

	public void save() {
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

}

package org.seasar.chronos.threadpool;

import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.ThreadPoolType;

public class ThreadPool implements TaskThreadPool {

	private ThreadPoolType threadPoolType;

	private int threadPoolSize;

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

}

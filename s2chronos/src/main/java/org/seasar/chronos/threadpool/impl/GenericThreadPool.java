package org.seasar.chronos.threadpool.impl;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.threadpool.ThreadPool;

public class GenericThreadPool implements ThreadPool {

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

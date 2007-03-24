package org.seasar.chronos.threadpool;

import org.seasar.chronos.ThreadPoolType;

public interface ThreadPool {

	public void setThreadPoolType(ThreadPoolType threadPoolType);

	public void setThreadPoolSize(int threadPoolSize);

	public ThreadPoolType getThreadPoolType();

	public int getThreadPoolSize();

}

package org.seasar.chronos;


public interface TaskThreadPool {

	public void setThreadPoolType(ThreadPoolType threadPoolType);

	public void setThreadPoolSize(int threadPoolSize);

	public ThreadPoolType getThreadPoolType();

	public int getThreadPoolSize();

}

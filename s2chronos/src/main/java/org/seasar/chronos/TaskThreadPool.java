package org.seasar.chronos;

import java.io.Serializable;

public interface TaskThreadPool extends Serializable {

	public void setThreadPoolType(ThreadPoolType threadPoolType);

	public void setThreadPoolSize(int threadPoolSize);

	public ThreadPoolType getThreadPoolType();

	public int getThreadPoolSize();

}

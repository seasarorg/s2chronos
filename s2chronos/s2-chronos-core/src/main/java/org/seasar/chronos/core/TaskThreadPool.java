package org.seasar.chronos.core;

import java.io.Serializable;

public interface TaskThreadPool extends Serializable {

	public void setThreadPoolType(ThreadPoolType threadPoolType);

	public void setThreadPoolSize(Integer threadPoolSize);

	public ThreadPoolType getThreadPoolType();

	public Integer getThreadPoolSize();

}
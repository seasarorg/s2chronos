package org.seasar.chronos.core;

public interface TaskThreadPool extends Serializable {

	public Long getThreadPoolId();

	public Integer getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public void setThreadPoolId(Long id);

	public void setThreadPoolSize(Integer threadPoolSize);

	public void setThreadPoolType(ThreadPoolType threadPoolType);

}

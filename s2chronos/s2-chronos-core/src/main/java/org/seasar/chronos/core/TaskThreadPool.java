package org.seasar.chronos.core;

public interface TaskThreadPool extends Serializable {

	public void setId(Integer id);

	public Integer getId();

	public void setThreadPoolType(ThreadPoolType threadPoolType);

	public void setThreadPoolSize(Integer threadPoolSize);

	public ThreadPoolType getThreadPoolType();

	public Integer getThreadPoolSize();

}

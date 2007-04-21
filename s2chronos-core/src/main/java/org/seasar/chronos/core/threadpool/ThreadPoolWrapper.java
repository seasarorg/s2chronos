package org.seasar.chronos.core.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;

public class ThreadPoolWrapper implements TaskThreadPool {

	private static final long serialVersionUID = 1563433734900701359L;

	private TaskThreadPool taskThreadPool;

	public ThreadPoolWrapper(TaskThreadPool taskThreadPool) {
		this.taskThreadPool = taskThreadPool;
	}

	public Long getThreadPoolId() {
		return taskThreadPool.getThreadPoolId();
	}

	public Integer getThreadPoolSize() {
		return taskThreadPool.getThreadPoolSize();
	}

	public ThreadPoolType getThreadPoolType() {
		return taskThreadPool.getThreadPoolType();
	}

	public void load() {
		taskThreadPool.load();
	}

	public void save() {
		taskThreadPool.save();
	}

	public void setThreadPoolId(Long id) {
		taskThreadPool.setThreadPoolId(id);
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		taskThreadPool.setThreadPoolSize(threadPoolSize);
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		taskThreadPool.setThreadPoolType(threadPoolType);
	}

}

package org.seasar.chronos.core.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.util.ObjectUtil;

public class ThreadPool implements TaskThreadPool {

	private static final long serialVersionUID = 3092612895816238852L;

	private Long id;

	private ThreadPoolType threadPoolType = ThreadPoolType.CACHED;

	private Integer threadPoolSize;

	public Long getThreadPoolId() {
		if (id == null) {
			id = ObjectUtil.generateObjectId();
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

	public void setThreadPoolId(Long id) {
		this.id = id;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

}

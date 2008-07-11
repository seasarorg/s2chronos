/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.threadpool;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;

public class ThreadPoolWrapper implements TaskThreadPool {

	private static final long serialVersionUID = 1563433734900701359L;

	private final TaskThreadPool taskThreadPool;

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

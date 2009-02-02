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
package org.seasar.chronos.core.model.threadpool;

import org.seasar.chronos.core.model.TaskThreadPool;
import org.seasar.chronos.core.model.ThreadPoolType;
import org.seasar.chronos.core.util.ObjectUtil;

public class ThreadPool implements TaskThreadPool {

	private static final long serialVersionUID = 3092612895816238852L;

	private Long id;

	private ThreadPoolType threadPoolType = ThreadPoolType.CACHED;

	private Integer threadPoolSize;

	public ThreadPool(ThreadPoolType threadPoolType, Integer threadPoolSize) {
		this.setThreadPoolType(threadPoolType);
		this.setThreadPoolSize(threadPoolSize);
	}

	public ThreadPool(ThreadPoolType threadPoolType) {
		this(threadPoolType, null);
	}

	public ThreadPool() {
		this(null, null);
	}

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

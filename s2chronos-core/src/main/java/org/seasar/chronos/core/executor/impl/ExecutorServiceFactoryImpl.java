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
package org.seasar.chronos.core.executor.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.executor.ExecutorServiceFactory;
import org.seasar.chronos.core.threadpool.ThreadPool;
import org.seasar.framework.log.Logger;

public class ExecutorServiceFactoryImpl implements ExecutorServiceFactory {

	private static Logger log = Logger
			.getLogger(ExecutorServiceFactoryImpl.class);

	private final AtomicLong threadGroupNo = new AtomicLong();

	private static class ThreadFactoryImpl implements ThreadFactory {

		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		private boolean daemon = false;

		public ThreadFactoryImpl(boolean daemon) {
			this.daemon = daemon;
			SecurityManager securitymanager = System.getSecurityManager();
			group = securitymanager == null ? Thread.currentThread()
					.getThreadGroup() : securitymanager.getThreadGroup();
			StringBuilder sb = new StringBuilder();
			sb.append("chronos-").append("pool-").append(
					poolNumber.getAndIncrement());
			sb.append("-");
			if (daemon) {
				sb.append("daemon");
			}
			sb.append("thread-");
			namePrefix = sb.toString();
		}

		public Thread newThread(Runnable runnable) {
			StringBuilder sb = new StringBuilder();
			sb.append(namePrefix).append(threadNumber.getAndIncrement());
			Thread thread = new Thread(group, runnable, sb.toString(), 0L);
			thread.setDaemon(this.daemon);
			if (thread.getPriority() != 5) {
				thread.setPriority(5);
			}
			return thread;

		}
	}

	public ExecutorService create(ThreadPoolType type, Integer threadPoolSize) {
		return this.create(type, threadPoolSize, false);
	}

	public ExecutorService create(ThreadPoolType type, Integer threadPoolSize,
			boolean daemon) {
		ExecutorService executorService = null;
		this.threadGroupNo.addAndGet(1L);
		if (type.equals(ThreadPoolType.CACHED)) {
			executorService = Executors
					.newCachedThreadPool(new ThreadFactoryImpl(daemon));
		} else if (type.equals(ThreadPoolType.FIXED)) {
			executorService = Executors.newFixedThreadPool(threadPoolSize,
					new ThreadFactoryImpl(daemon));
		} else if (type.equals(ThreadPoolType.SCHEDULED)) {
			executorService = Executors.newScheduledThreadPool(threadPoolSize,
					new ThreadFactoryImpl(daemon));
		} else if (type.equals(ThreadPoolType.SINGLE)) {
			executorService = Executors
					.newSingleThreadExecutor(new ThreadFactoryImpl(daemon));
		}
		return executorService;
	}

	public ExecutorService create(ThreadPool threadPool) {
		return this.create(threadPool.getThreadPoolType(), threadPool
				.getThreadPoolSize(), false);
	}

	public ExecutorService create(ThreadPool threadPool, boolean daemon) {
		return this.create(threadPool.getThreadPoolType(), threadPool
				.getThreadPoolSize(), daemon);
	}

}

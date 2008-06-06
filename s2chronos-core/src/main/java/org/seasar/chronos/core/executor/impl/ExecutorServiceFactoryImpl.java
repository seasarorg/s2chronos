package org.seasar.chronos.core.executor.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.executor.ExecutorServiceFactory;
import org.seasar.chronos.core.threadpool.ThreadPool;
import org.seasar.framework.log.Logger;

public class ExecutorServiceFactoryImpl implements ExecutorServiceFactory {
	private static Logger log = Logger
			.getLogger(ExecutorServiceFactoryImpl.class);

	public ExecutorService create(ThreadPoolType type, Integer threadPoolSize) {
		log.debug("ExecutorServiceFactoryImpl#create");
		ExecutorService executorService = null;
		if (type.equals(ThreadPoolType.CACHED)) {
			executorService = Executors.newCachedThreadPool();
		} else if (type.equals(ThreadPoolType.FIXED)) {
			executorService = Executors.newFixedThreadPool(threadPoolSize);
		} else if (type.equals(ThreadPoolType.SCHEDULED)) {
			executorService = Executors.newScheduledThreadPool(threadPoolSize);
		} else if (type.equals(ThreadPoolType.SINGLE)) {
			executorService = Executors.newSingleThreadExecutor();
		}
		return executorService;
	}

	public ExecutorService create(ThreadPool threadPool) {
		return this.create(threadPool.getThreadPoolType(), threadPool
				.getThreadPoolSize());
	}
}

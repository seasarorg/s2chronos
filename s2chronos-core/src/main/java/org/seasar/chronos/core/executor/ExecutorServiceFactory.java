package org.seasar.chronos.core.executor;

import java.util.concurrent.ExecutorService;

import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.threadpool.ThreadPool;

public interface ExecutorServiceFactory {

	public ExecutorService create(ThreadPoolType type, Integer threadPoolSize);

	public ExecutorService create(ThreadPool threadPool);

}

package org.seasar.chronos.core.util;

import java.util.Map;

import org.seasar.chronos.core.model.TaskThreadPool;
import org.seasar.framework.util.tiger.CollectionsUtil;

public final class TaskThreadPoolCacheUtil {

	private static Map<String, TaskThreadPool> threadPoolCache = CollectionsUtil
			.newConcurrentHashMap();

	public static void putTaskThreadPool(String key, TaskThreadPool threadPool) {
		threadPoolCache.put(key, threadPool);
	}

	public static TaskThreadPool getTaskThreadPool(String key) {
		return threadPoolCache.get(key);
	}

}

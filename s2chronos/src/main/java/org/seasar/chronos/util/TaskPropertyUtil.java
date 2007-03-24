package org.seasar.chronos.util;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.chronos.threadpool.ThreadPool;
import org.seasar.chronos.trigger.Trigger;

public final class TaskPropertyUtil {

	public static boolean getShutdownTask(TaskProperties prop) {
		boolean shutdown = prop.getShutdownTask();
		return shutdown;
	}

	public static void setShutdownTask(TaskProperties prop, boolean shutdownTask) {
		prop.setShutdownTask(shutdownTask);
	}

	public static boolean getEndTask(TaskProperties prop) {
		boolean end = false;
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			end = prop.getEndTask();
		} else {
			end = trigger.getEndTask();
		}
		return end;
	}

	public static void setEndTask(TaskProperties prop, boolean endTask) {
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			prop.setEndTask(endTask);
		} else {
			trigger.setEndTask(endTask);
		}
	}

	public static boolean getStartTask(TaskProperties prop) {
		boolean start = false;
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			start = prop.getStartTask();
			prop.setStartTask(false);
		} else {
			start = trigger.getStartTask();
		}
		return start;
	}

	public static void setStartTask(TaskProperties prop, boolean startTask) {
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			prop.setStartTask(startTask);
		} else {
			trigger.setStartTask(startTask);
		}
	}

	public static ThreadPoolType getThreadPoolType(TaskProperties prop) {
		ThreadPoolType threadPoolType = null;
		ThreadPool threadPool = prop.getThreadPool();
		if (threadPool == null) {
			return prop.getThreadPoolType();
		} else {
			threadPool.getThreadPoolType();
		}
		return threadPoolType;
	}

	public static int getThreadPoolSize(TaskProperties prop) {
		int threadPoolSize = 1;
		ThreadPool threadPool = prop.getThreadPool();
		if (threadPool == null) {
			return prop.getThreadPoolSize();
		} else {
			threadPool.getThreadPoolSize();
		}
		return threadPoolSize;
	}

}

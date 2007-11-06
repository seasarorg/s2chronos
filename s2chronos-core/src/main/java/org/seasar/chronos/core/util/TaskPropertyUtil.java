package org.seasar.chronos.core.util;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskProperties;

public final class TaskPropertyUtil {

	public static String getDescription(TaskProperties prop) {
		String result = prop.getDescription();
		return result;
	}

	public static boolean isEndTask(TaskProperties prop) {
		boolean end = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			end = prop.isEndTask();
		} else {
			end = taskTrigger.getEndTask();
		}
		return end;
	}

	public static boolean isShutdownTask(TaskProperties prop) {
		boolean shutdown = prop.isShutdownTask();
		return shutdown;
	}

	public static boolean isStartTask(TaskProperties prop) {
		boolean start = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			start = prop.isStartTask();
		} else {
			start = taskTrigger.getStartTask();
		}
		return start;
	}

	public static long getTaskId(TaskProperties prop) {
		long result = prop.getTaskId();
		return result;
	}

	public static String getTaskName(TaskProperties prop) {
		String taskName = prop.getTaskName();
		if (taskName == null) {
			Class<?> clazz = prop.getTaskClass();
			Task task = (Task) clazz.getAnnotation(Task.class);
			return task != null ? task.name() : taskName;
		}
		return taskName;
	}

	public static int getThreadPoolSize(TaskProperties prop) {
		int threadPoolSize = 1;
		TaskThreadPool taskThreadPool = prop.getThreadPool();
		if (taskThreadPool == null) {
			return prop.getThreadPoolSize();
		} else {
			taskThreadPool.getThreadPoolSize();
		}
		return threadPoolSize;
	}

	public static ThreadPoolType getThreadPoolType(TaskProperties prop) {
		ThreadPoolType threadPoolType = null;
		TaskThreadPool taskThreadPool = prop.getThreadPool();
		if (taskThreadPool == null) {
			return prop.getThreadPoolType();
		} else {
			taskThreadPool.getThreadPoolType();
		}
		return threadPoolType;
	}

	public static void setEndTask(TaskProperties prop, boolean endTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setEndTask(endTask);
		} else {
			taskTrigger.setEndTask(endTask);
		}
	}

	public static void setShutdownTask(TaskProperties prop, boolean shutdownTask) {
		prop.setShutdownTask(shutdownTask);
	}

	public static void setStartTask(TaskProperties prop, boolean startTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setStartTask(startTask);
		} else {
			taskTrigger.setStartTask(startTask);
		}
	}

	public static boolean isReSchedule(TaskProperties prop) {
		boolean reSchedule = prop.isReSchedule();
		return reSchedule;
	}

}

package org.seasar.chronos.util;

import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.task.TaskProperties;

public final class TaskPropertyUtil {

	public static String getTaskName(TaskProperties prop) {
		String taskName = prop.getTaskName();
		if (taskName == null) {
			Class clazz = prop.getTaskComponentDef().getComponentClass();
			Task task = (Task) clazz.getAnnotation(Task.class);
			return task != null ? task.name() : taskName;
		}
		return taskName;
	}

	public static boolean getShutdownTask(TaskProperties prop) {
		boolean shutdown = prop.getShutdownTask();
		return shutdown;
	}

	public static void setShutdownTask(TaskProperties prop, boolean shutdownTask) {
		prop.setShutdownTask(shutdownTask);
	}

	public static boolean getEndTask(TaskProperties prop) {
		boolean end = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			end = prop.getEndTask();
		} else {
			end = taskTrigger.getEndTask();
		}
		return end;
	}

	public static void setEndTask(TaskProperties prop, boolean endTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setEndTask(endTask);
		} else {
			taskTrigger.setEndTask(endTask);
		}
	}

	public static boolean getStartTask(TaskProperties prop) {
		boolean start = false;
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			start = prop.getStartTask();
			prop.setStartTask(false);
		} else {
			start = taskTrigger.getStartTask();
		}
		return start;
	}

	public static void setStartTask(TaskProperties prop, boolean startTask) {
		TaskTrigger taskTrigger = prop.getTrigger();
		if (taskTrigger == null) {
			prop.setStartTask(startTask);
		} else {
			taskTrigger.setStartTask(startTask);
		}
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

}

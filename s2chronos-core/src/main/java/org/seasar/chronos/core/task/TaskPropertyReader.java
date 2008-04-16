package org.seasar.chronos.core.task;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.framework.beans.BeanDesc;

public interface TaskPropertyReader {

	public void loadTask(Object task, Class<?> taskClass);

	public void loadTask(Object task, BeanDesc beanDesc);

	public boolean hasTaskId();

	public boolean hasTaskName();

	public boolean hasDescription();

	public boolean hasStartTask();

	public boolean hasEndTask();

	public boolean hasShutdownTask();

	public boolean hasExecuted();

	public boolean hasReSchedule();

	public boolean hasTrigger();

	public boolean hasThreadPool();

	public boolean hasThreadPoolSize();

	public boolean hasThreadPoolType();

	public Long getTaskId(Long defaultValue);

	public String getTaskName(String defaultValue);

	public String getDescription(String defaultValue);

	public boolean isStartTask(boolean defaultValue);

	public boolean isEndTask(boolean defaultValue);

	public boolean isShutdownTask(boolean defaultValue);

	public boolean isExecuted(boolean defaultValue);

	public boolean isReSchedule(boolean defaultValue);

	public TaskTrigger getTrigger(TaskTrigger defaultValue);

	public TaskThreadPool getThreadPool(TaskThreadPool defaultValue);

	public int getThreadPoolSize(int defaultValue);

	public ThreadPoolType getThreadPoolType(ThreadPoolType defaultValue);

}

package org.seasar.chronos.task;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;

public interface TaskProperties {

	public String getTaskName();

	public void setScheduler(Scheduler scheduler);

	public Scheduler getScheduler();

	public void setTaskClass(Class taskClass);

	public Class getTaskClass();

	public void setTask(Object task);

	public Object getTask();

	public void setGetterSignal(Object getterSignal);

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public void setStartTask(boolean startTask);

	public boolean getStartTask();

	public boolean getEndTask();

	public void setEndTask(boolean endTask);

	public boolean getShutdownTask();

	public void setShutdownTask(boolean shutdownTask);

	public TaskTrigger getTrigger();

	// public void setTrigger(TaskTrigger taskTrigger);

	public TaskThreadPool getThreadPool();

	public void setThreadPool(TaskThreadPool taskThreadPool);

}

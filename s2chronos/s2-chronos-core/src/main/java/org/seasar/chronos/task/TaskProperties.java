package org.seasar.chronos.task;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;

public interface TaskProperties {

	public boolean getEndTask();

	public Scheduler getScheduler();

	public boolean getShutdownTask();

	public boolean getStartTask();

	public Object getTask();

	public Class getTaskClass();

	public void setTaskId(int taskId);

	public int getTaskId();

	public String getTaskName();

	public TaskThreadPool getThreadPool();

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public TaskTrigger getTrigger();

	public boolean isExecute();

	public void setEndTask(boolean endTask);

	public void setExecute(boolean executed);

	public void setGetterSignal(Object getterSignal);

	public void setScheduler(Scheduler scheduler);

	public void setShutdownTask(boolean shutdownTask);

	public void setTrigger(TaskTrigger taskTrigger);

	public void setStartTask(boolean startTask);

	public void setTask(Object task);

	public void setTaskClass(Class taskClass);

	public void setThreadPool(TaskThreadPool taskThreadPool);
}

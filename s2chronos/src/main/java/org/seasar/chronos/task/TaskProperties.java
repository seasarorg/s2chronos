package org.seasar.chronos.task;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.framework.container.ComponentDef;

public interface TaskProperties {

	public String getTaskName();

	public void setScheduler(Scheduler scheduler);

	public Scheduler getScheduler();

	public void setTaskComponentDef(ComponentDef taskComponentDef);

	public ComponentDef getTaskComponentDef();

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

	public void setTrigger(TaskTrigger taskTrigger);

	public TaskThreadPool getThreadPool();

	public void setThreadPool(TaskThreadPool taskThreadPool);

}

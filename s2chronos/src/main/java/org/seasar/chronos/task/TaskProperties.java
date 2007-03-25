package org.seasar.chronos.task;

import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.TaskTrigger;
import org.seasar.framework.container.ComponentDef;

public interface TaskProperties {

	public void setTaskComponentDef(ComponentDef taskComponentDef);

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

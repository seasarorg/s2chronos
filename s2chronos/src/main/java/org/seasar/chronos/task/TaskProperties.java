package org.seasar.chronos.task;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.trigger.Trigger;
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

	public Trigger getTrigger();

	public void setTrigger(Trigger trigger);

}

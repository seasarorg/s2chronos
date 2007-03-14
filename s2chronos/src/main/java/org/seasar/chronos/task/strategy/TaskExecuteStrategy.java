package org.seasar.chronos.task.strategy;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.task.handler.TaskExecuteHandler;
import org.seasar.framework.container.ComponentDef;

public interface TaskExecuteStrategy {

	public void setTaskGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler);

	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler);

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException;

	public void execute(String startJobName) throws InterruptedException;

	public void destroy() throws InterruptedException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public void setStartTask(boolean startTask);

	public boolean getStartTask();

	public boolean getEndTask();

	public void setEndTask(boolean endTask);

	public boolean getShutdownTask();

	public void setShutdownTask(boolean shutdownTask);

}
package org.seasar.chronos.job.strategy;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.job.handler.TaskExecuteHandler;
import org.seasar.framework.container.ComponentDef;

public interface JobExecuteStrategy {

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException;

	public void callJob(String startJobName) throws InterruptedException;

	public void destroy() throws InterruptedException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public boolean canExecute() throws InterruptedException;

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public void setJobGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler);

	public void setJobMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler);

}
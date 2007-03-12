package org.seasar.chronos.job;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.framework.container.ComponentDef;

public interface JobExecuteStrategy {

	public String initialize(ComponentDef jobComponentDef) throws Throwable;

	public void callJob(String startJobName) throws Throwable;

	public void destroy() throws Throwable;

	public void cancel();

	public boolean canExecute() throws Throwable;

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public void setJobGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler);

	public void setJobMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler);

}
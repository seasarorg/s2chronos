package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.chronos.job.JobExecuteContext;
import org.seasar.chronos.job.JobExecuteState;
import org.seasar.chronos.job.JobExecuteStrategy;
import org.seasar.framework.container.ComponentDef;

public abstract class AbstractJobExecuteState implements JobExecuteState {

	private JobExecuteStrategy jobExecuteStrategy;

	public JobExecuteStrategy getJobExecuteStrategy() {
		return jobExecuteStrategy;
	}

	public void setJobExecuteStrategy(JobExecuteStrategy jobExecuteStrategy) {
		this.jobExecuteStrategy = jobExecuteStrategy;
	}

	protected void changeState(JobExecuteContext context,
			JobExecuteState nextState) {
		context.changeState(nextState);
	}

	public abstract boolean await(JobExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public abstract void callJob(JobExecuteContext context, String startJobName)
			throws InterruptedException, InvalidNextJobMethodException,
			ExecutionException;

	public abstract boolean canExecute(JobExecuteContext context)
			throws InterruptedException, ExecutionException;

	public abstract void cancel(JobExecuteContext context);

	public abstract void destroy(JobExecuteContext context)
			throws InterruptedException;

	public abstract String initialize(JobExecuteContext context,
			ComponentDef jobComponentDef) throws InterruptedException;

}

package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.JobExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class JobExecuteStateInitialized extends AbstractJobExecuteState {

	private JobExecuteStateNonInitialized jobExecuteStateNonInitialized;

	public void setJobExecuteStateNoInitialized(
			JobExecuteStateNonInitialized jobExecuteStateNonInitialized) {
		this.jobExecuteStateNonInitialized = jobExecuteStateNonInitialized;
	}

	@Override
	public boolean await(JobExecuteContext context, long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.getJobExecuteStrategy().await(time, timeUnit);
	}

	@Override
	public void execute(JobExecuteContext context, String startTaskName)
			throws InterruptedException {
		this.getJobExecuteStrategy().execute(startTaskName);
	}

	@Override
	public boolean canExecute(JobExecuteContext context)
			throws InterruptedException {
		return this.getJobExecuteStrategy().canExecute();
	}

	@Override
	public void cancel(JobExecuteContext context) {
		this.getJobExecuteStrategy().cancel();
	}

	@Override
	public void destroy(JobExecuteContext context) throws InterruptedException {
		this.getJobExecuteStrategy().destroy();
		this.changeState(context, jobExecuteStateNonInitialized);
	}

	@Override
	public String initialize(JobExecuteContext context,
			ComponentDef jobComponentDef) {
		return null;
	}

}

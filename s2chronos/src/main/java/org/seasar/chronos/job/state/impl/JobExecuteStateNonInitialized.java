package org.seasar.chronos.job.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.job.state.JobExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class JobExecuteStateNonInitialized extends AbstractJobExecuteState {

	private JobExecuteStateInitialized jobExecuteStateInitialized;

	public void setJobExecuteStateInitialized(
			JobExecuteStateInitialized jobExecuteStateInitialized) {
		this.jobExecuteStateInitialized = jobExecuteStateInitialized;
	}

	public boolean await(JobExecuteContext context, long time, TimeUnit timeUnit)
			throws InterruptedException {
		return false;
	}

	public void callJob(JobExecuteContext context, String startJobName)
			throws InterruptedException {

	}

	public boolean canExecute(JobExecuteContext context)
			throws InterruptedException {
		return false;
	}

	public void cancel(JobExecuteContext context) {
	}

	public void destroy(JobExecuteContext context) {
	}

	public String initialize(JobExecuteContext context,
			ComponentDef jobComponentDef) throws InterruptedException {

		String result = this.getJobExecuteStrategy()
				.initialize(jobComponentDef);
		this.changeState(context, jobExecuteStateInitialized);

		return result;
	}

}

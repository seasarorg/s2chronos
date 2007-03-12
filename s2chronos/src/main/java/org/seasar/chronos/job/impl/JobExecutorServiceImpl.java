package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.chronos.job.JobExecuteContext;
import org.seasar.chronos.job.JobExecutorService;
import org.seasar.framework.container.ComponentDef;

public class JobExecutorServiceImpl implements JobExecutorService {

	private JobExecuteContext jobExecuteContext;

	private JobExecuteStateNoInitialized jobExecuteStateNoInitialized;

	public JobExecutorServiceImpl() {

	}

	public void setJobExecuteStateNoInitialized(
			JobExecuteStateNoInitialized jobExecuteStateNoInitialized) {
		this.jobExecuteStateNoInitialized = jobExecuteStateNoInitialized;
	}

	public void setJobExecuteContext(JobExecuteContext jobExecuteContext) {
		this.jobExecuteContext = jobExecuteContext;

	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return jobExecuteContext.await(time, timeUnit);
	}

	public void callJob(String startJobName) throws InterruptedException,
			InvalidNextJobMethodException, ExecutionException {
		this.jobExecuteContext.callJob(startJobName);
	}

	public boolean canExecute() throws InterruptedException, ExecutionException {
		return this.jobExecuteContext.canExecute();
	}

	public void cancel() {
		this.jobExecuteContext.cancel();
	}

	public void destroy() throws Throwable {
		this.jobExecuteContext.destroy();
	}

	public String initialize(ComponentDef jobComponentDef) throws Throwable {
		this.jobExecuteContext.changeState(this.jobExecuteStateNoInitialized);
		return this.jobExecuteContext.initialize(jobComponentDef);
	}

}

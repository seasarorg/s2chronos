package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.chronos.job.JobExecutorService;
import org.seasar.chronos.job.state.JobExecuteContext;
import org.seasar.chronos.job.state.impl.JobExecuteStateNonInitialized;
import org.seasar.framework.container.ComponentDef;

public class JobExecutorServiceImpl implements JobExecutorService {

	private JobExecuteContext jobExecuteContext;

	private JobExecuteStateNonInitialized jobExecuteStateNonInitialized;

	public JobExecutorServiceImpl() {

	}

	public void setJobExecuteStateNonInitialized(
			JobExecuteStateNonInitialized jobExecuteStateNonInitialized) {
		this.jobExecuteStateNonInitialized = jobExecuteStateNonInitialized;
	}

	public void setJobExecuteContext(JobExecuteContext jobExecuteContext) {
		this.jobExecuteContext = jobExecuteContext;

	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return jobExecuteContext.await(time, timeUnit);
	}

	public void execute(String startJobName) throws InterruptedException,
			InvalidNextJobMethodException, ExecutionException {
		this.jobExecuteContext.execute(startJobName);
	}

	public boolean canExecute() throws InterruptedException, ExecutionException {
		return this.jobExecuteContext.canExecute();
	}

	public void cancel() {
		this.jobExecuteContext.cancel();
	}

	public void destroy() throws InterruptedException {
		this.jobExecuteContext.destroy();
	}

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {
		this.jobExecuteContext.changeState(this.jobExecuteStateNonInitialized);
		return this.jobExecuteContext.initialize(jobComponentDef);
	}

}

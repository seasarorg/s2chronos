package org.seasar.chronos.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.task.state.JobExecuteContext;
import org.seasar.chronos.task.state.impl.JobExecuteStateNonInitialized;
import org.seasar.framework.container.ComponentDef;

public class JobExecutorServiceImpl implements TaskExecutorService {

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

	public void execute(String startTaskName) throws InterruptedException {
		this.jobExecuteContext.execute(startTaskName);
	}

	public boolean canExecute() throws InterruptedException {
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

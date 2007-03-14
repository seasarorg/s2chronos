package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class TaskExecuteStateInitialized extends AbstractJobExecuteState {

	private TaskExecuteStateNonInitialized taskExecuteStateNonInitialized;

	public void setJobExecuteStateNoInitialized(
			TaskExecuteStateNonInitialized taskExecuteStateNonInitialized) {
		this.taskExecuteStateNonInitialized = taskExecuteStateNonInitialized;
	}

	@Override
	public boolean await(TaskExecuteContext context, long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.getJobExecuteStrategy().await(time, timeUnit);
	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {
		this.getJobExecuteStrategy().execute(startTaskName);
	}

	@Override
	public boolean canExecute(TaskExecuteContext context)
			throws InterruptedException {
		return this.getJobExecuteStrategy().canExecute();
	}

	@Override
	public void cancel(TaskExecuteContext context) {
		this.getJobExecuteStrategy().cancel();
	}

	@Override
	public void destroy(TaskExecuteContext context) throws InterruptedException {
		this.getJobExecuteStrategy().destroy();
		this.changeState(context, taskExecuteStateNonInitialized);
	}

	@Override
	public String initialize(TaskExecuteContext context,
			ComponentDef jobComponentDef) {
		return null;
	}

}

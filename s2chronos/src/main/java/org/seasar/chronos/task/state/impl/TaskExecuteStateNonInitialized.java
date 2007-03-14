package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class TaskExecuteStateNonInitialized extends AbstractJobExecuteState {

	private TaskExecuteStateInitialized taskExecuteStateInitialized;

	public void setTaskExecuteStateInitialized(
			TaskExecuteStateInitialized taskExecuteStateInitialized) {
		this.taskExecuteStateInitialized = taskExecuteStateInitialized;
	}

	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return false;
	}

	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {

	}

	public void cancel(TaskExecuteContext context) {
	}

	public void destroy(TaskExecuteContext context) {
	}

	public String initialize(TaskExecuteContext context,
			ComponentDef jobComponentDef) throws InterruptedException {

		String result = this.getTaskExecuteStrategy().initialize(
				jobComponentDef);
		this.changeState(context, taskExecuteStateInitialized);

		return result;
	}

}

package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class TaskExecuteStateNonInitialized extends AbstractTaskExecuteState {

	private TaskExecuteStateInitialized taskExecuteStateInitialized;

	public void setTaskExecuteStateInitialized(
			TaskExecuteStateInitialized taskExecuteStateInitialized) {
		this.taskExecuteStateInitialized = taskExecuteStateInitialized;
	}

	@Override
	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.getTaskExecuteStrategy().setTaskComponentDef(taskComponentDef);

	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return false;
	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {

	}

	@Override
	public boolean cancel(TaskExecuteContext context) {
		return false;
	}

	@Override
	public void destroy(TaskExecuteContext context) {

	}

	@Override
	public String initialize(TaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().initialize();
		this.changeState(context, taskExecuteStateInitialized);
		return result;
	}

	@Override
	public void prepare(TaskExecuteContext context) {
		this.getTaskExecuteStrategy().prepare();
	}

	@Override
	public void waitOne() {

	}

}

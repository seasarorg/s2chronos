package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.TaskExecuteState;
import org.seasar.framework.container.ComponentDef;

public class TaskExecuteStateInitialized extends AbstractTaskExecuteState {

	private TaskExecuteState taskExecuteStateNonInitialized;

	public void setTaskExecuteStateNonInitialized(
			TaskExecuteState taskExecuteStateNonInitialized) {
		this.taskExecuteStateNonInitialized = taskExecuteStateNonInitialized;
	}

	@Override
	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.getTaskExecuteStrategy().setTaskComponentDef(taskComponentDef);
	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return this.getTaskExecuteStrategy().await(time, timeUnit);
	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {
		this.getTaskExecuteStrategy().execute(startTaskName);
	}

	@Override
	public boolean cancel(TaskExecuteContext context) {
		log.debug("TaskExecuteStateInitialized.cancel start");
		boolean result = this.getTaskExecuteStrategy().cancel();
		log.debug("TaskExecuteStateInitialized.cancel end");
		return result;
	}

	@Override
	public void destroy(TaskExecuteContext context) throws InterruptedException {
		this.getTaskExecuteStrategy().destroy();
		this.changeState(context, taskExecuteStateNonInitialized);
	}

	@Override
	public String initialize(TaskExecuteContext context)
			throws InterruptedException {
		return null;
	}

	@Override
	public void prepare(TaskExecuteContext context) {

	}

	@Override
	public void waitOne() throws InterruptedException {
		this.getTaskExecuteStrategy().waitOne();

	}

}

package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateNonInitialized extends AbstractTaskExecuteState {

	public TaskExecuteStateNonInitialized(
			TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return true;
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
	public void destroy(AbstractTaskExecuteContext context) {

	}

	@Override
	public String initialize(AbstractTaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().initialize();
		this.changeState(context, context.getTaskExecuteStateInitialized());
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

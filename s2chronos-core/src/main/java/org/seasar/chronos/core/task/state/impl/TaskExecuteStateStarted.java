package org.seasar.chronos.core.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateStarted extends AbstractTaskExecuteState {

	public TaskExecuteStateStarted(TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return this.getTaskExecuteStrategy().await(time, timeUnit);

	}

	@Override
	public boolean cancel(TaskExecuteContext context) {
		boolean result = this.getTaskExecuteStrategy().cancel();
		return result;
	}

	@Override
	public void destroy(AbstractTaskExecuteContext context) {

	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {
		this.getTaskExecuteStrategy().execute(startTaskName);
	}

	@Override
	public String finish(AbstractTaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().finish();
		this.changeState(context, context.getTaskExecuteStateFinished());
		return result;
	}

	@Override
	public void initialize(AbstractTaskExecuteContext context) {

	}

	@Override
	public String start(AbstractTaskExecuteContext context)
			throws InterruptedException {
		return null;
	}

	@Override
	public void waitOne() throws InterruptedException {
		this.getTaskExecuteStrategy().waitOne();
	}

}

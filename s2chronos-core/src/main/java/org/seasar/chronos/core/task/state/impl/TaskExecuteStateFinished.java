package org.seasar.chronos.core.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateFinished extends AbstractTaskExecuteState {

	public TaskExecuteStateFinished(TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return false;
	}

	@Override
	public boolean cancel(TaskExecuteContext context) {
		return false;
	}

	@Override
	public void destroy(AbstractTaskExecuteContext context)
			throws InterruptedException {
		this.getTaskExecuteStrategy().destroy();
		this.changeState(context, context.getTaskExecuteStateInitialized());
	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {

	}

	@Override
	public String finish(AbstractTaskExecuteContext context)
			throws InterruptedException {
		return null;
	}

	@Override
	public void initialize(AbstractTaskExecuteContext context) {

	}

	@Override
	public String start(AbstractTaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().start();
		this.changeState(context, context.getTaskExecuteStateStarted());
		return result;
	}

	@Override
	public void waitOne() throws InterruptedException {

	}

}

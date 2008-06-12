package org.seasar.chronos.core.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateInitialized extends AbstractTaskExecuteState {

	private static final long serialVersionUID = -8369367234393535056L;

	public TaskExecuteStateInitialized(TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
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
		boolean result = this.getTaskExecuteStrategy().cancel();
		return result;
	}

	@Override
	public String destroy(AbstractTaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().destroy();
		this.changeState(context, context.getTaskExecuteStateNonInitialize());
		return result;
	}

	@Override
	public String initialize(AbstractTaskExecuteContext context)
			throws InterruptedException {
		return null;
	}

	@Override
	public void waitOne() throws InterruptedException {
		this.getTaskExecuteStrategy().waitOne();

	}

}

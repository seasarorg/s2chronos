package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateInitialized extends AbstractTaskExecuteState {

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
		log.debug("TaskExecuteStateInitialized.cancel start");
		boolean result = this.getTaskExecuteStrategy().cancel();
		log.debug("TaskExecuteStateInitialized.cancel end");
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
	public void prepare(TaskExecuteContext context) {

	}

	@Override
	public void waitOne() throws InterruptedException {
		this.getTaskExecuteStrategy().waitOne();

	}

}

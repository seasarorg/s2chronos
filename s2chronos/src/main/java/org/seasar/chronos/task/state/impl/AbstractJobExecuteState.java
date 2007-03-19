package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.TaskExecuteState;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;

public abstract class AbstractJobExecuteState implements TaskExecuteState {

	private TaskExecuteStrategy taskExecuteStrategy;

	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return taskExecuteStrategy;
	}

	public void setTaskExecuteStrategy(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	protected void changeState(TaskExecuteContext context,
			TaskExecuteState nextState) {
		context.changeState(nextState);
	}

	public abstract void setTaskComponentDef(ComponentDef taskComponentDef);

	public abstract void waitOne() throws InterruptedException;

	public abstract boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public abstract void execute(TaskExecuteContext context,
			String startTaskName) throws InterruptedException;

	public abstract void cancel(TaskExecuteContext context);

	public abstract void destroy(TaskExecuteContext context)
			throws InterruptedException;

	public abstract void prepare(TaskExecuteContext context);

	public abstract String initialize(TaskExecuteContext context)
			throws InterruptedException;

}

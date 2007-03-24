package org.seasar.chronos.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.TaskExecuteState;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;

public abstract class AbstractTaskExecuteState implements TaskExecuteState {

	protected static Logger log = Logger
			.getLogger(AbstractTaskExecuteState.class);

	private TaskExecuteStrategy taskExecuteStrategy;

	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return taskExecuteStrategy;
	}

	public void setTaskExecuteStrategy(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	protected synchronized void changeState(TaskExecuteContext context,
			TaskExecuteState nextState) {
		context.changeState(nextState);
	}

	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.taskExecuteStrategy.setTaskComponentDef(taskComponentDef);
	}

	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteStrategy.setGetterSignal(getterSignal);
	}

	public abstract void waitOne() throws InterruptedException;

	public abstract boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public abstract void execute(TaskExecuteContext context,
			String startTaskName) throws InterruptedException;

	public abstract boolean cancel(TaskExecuteContext context);

	public abstract void destroy(TaskExecuteContext context)
			throws InterruptedException;

	public abstract void prepare(TaskExecuteContext context);

	public abstract String initialize(TaskExecuteContext context)
			throws InterruptedException;

}

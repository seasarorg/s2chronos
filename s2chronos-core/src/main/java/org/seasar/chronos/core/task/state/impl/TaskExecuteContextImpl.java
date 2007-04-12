package org.seasar.chronos.core.task.state.impl;

import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public class TaskExecuteContextImpl extends AbstractTaskExecuteContext {

	public TaskExecuteContextImpl(TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	private static final long serialVersionUID = -7989989930951089457L;

}

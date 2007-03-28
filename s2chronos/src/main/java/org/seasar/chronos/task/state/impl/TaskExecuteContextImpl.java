package org.seasar.chronos.task.state.impl;

import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.task.strategy.impl.TaskExecuteStrategyImpl;

public class TaskExecuteContextImpl extends AbstractTaskExecuteContext {

	@Override
	protected TaskExecuteStrategy createTaskExecuteStrategy() {
		return new TaskExecuteStrategyImpl();
	}

}

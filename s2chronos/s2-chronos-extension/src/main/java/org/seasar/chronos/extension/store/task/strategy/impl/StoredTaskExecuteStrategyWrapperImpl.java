package org.seasar.chronos.extension.store.task.strategy.impl;

import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategyWrapper;
import org.seasar.chronos.extension.store.TaskStore;

public class StoredTaskExecuteStrategyWrapperImpl extends
		TaskExecuteStrategyWrapper {

	private TaskStore taskStore;

	public StoredTaskExecuteStrategyWrapperImpl(
			TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	@Override
	public void load() {
		this.taskStore.loadFromStore(this.getTaskId(), this);
	}

	@Override
	public void save() {
		this.taskStore.saveToStore(this);
	}

	public void setTaskStore(TaskStore taskStore) {
		this.taskStore = taskStore;
	}

}

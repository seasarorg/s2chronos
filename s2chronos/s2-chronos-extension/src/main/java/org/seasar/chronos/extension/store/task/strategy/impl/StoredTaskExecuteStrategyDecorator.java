package org.seasar.chronos.extension.store.task.strategy.impl;

import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.core.task.strategy.impl.TaskExecuteStrategyDecocator;
import org.seasar.chronos.extension.store.TaskStore;

public class StoredTaskExecuteStrategyDecorator extends
		TaskExecuteStrategyDecocator {

	private TaskStore taskStore;

	public StoredTaskExecuteStrategyDecorator(
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

package org.seasar.chronos.store.task.strategy.impl;

import org.seasar.chronos.store.TaskStore;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;
import org.seasar.chronos.task.strategy.impl.TaskExecuteStrategyDecocator;

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

package org.seasar.chronos.core.task.strategy;

import org.seasar.chronos.core.Serializable;
import org.seasar.chronos.core.task.TaskMethods;
import org.seasar.chronos.core.task.TaskProperties;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties,
		Serializable {

	public boolean checkMoveAnotherTask(String nextTaskName);

}
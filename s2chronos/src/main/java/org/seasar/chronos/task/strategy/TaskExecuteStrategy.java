package org.seasar.chronos.task.strategy;

import java.io.Serializable;

import org.seasar.chronos.task.TaskMethods;
import org.seasar.chronos.task.TaskProperties;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties,
		Serializable {

	public boolean checkMoveAnotherTask(String nextTaskName);

}
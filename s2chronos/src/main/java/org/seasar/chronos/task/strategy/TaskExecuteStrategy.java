package org.seasar.chronos.task.strategy;

import java.io.Externalizable;

import org.seasar.chronos.task.TaskMethods;
import org.seasar.chronos.task.TaskProperties;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties,
		Externalizable {

	public boolean checkMoveAnotherTask(String nextTaskName);

}
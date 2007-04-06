package org.seasar.chronos.task.strategy;

import org.seasar.chronos.task.TaskMethods;
import org.seasar.chronos.task.TaskProperties;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties {

	public boolean checkMoveAnotherTask(String nextTaskName);

}
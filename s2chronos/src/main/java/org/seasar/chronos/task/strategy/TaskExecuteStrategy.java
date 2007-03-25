package org.seasar.chronos.task.strategy;

import org.seasar.chronos.task.TaskMethods;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.chronos.task.handler.TaskExecuteHandler;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties {

	public void setTaskGroupMethodExecuteHandler(
			TaskExecuteHandler taskGroupMethdoExecuteHandler);

	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler taskMethdoExecuteHandler);

	public boolean checkMoveAnotherTask(String nextTaskName);

}
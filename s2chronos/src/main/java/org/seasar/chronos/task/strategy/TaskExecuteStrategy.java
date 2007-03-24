package org.seasar.chronos.task.strategy;

import org.seasar.chronos.task.TaskMethods;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.chronos.task.handler.TaskExecuteHandler;

public interface TaskExecuteStrategy extends TaskMethods, TaskProperties {

	public void setTaskGroupMethodExecuteHandler(
			TaskExecuteHandler jobGroupMethdoExecuteHandler);

	public void setTaskMethodExecuteHandler(
			TaskExecuteHandler jobMethdoExecuteHandler);

	public boolean checkMoveAnotherTask(String nextTaskName);

}
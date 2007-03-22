package org.seasar.chronos.handler.impl;

import java.util.List;

import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskStateType;

public class ScheduleExecuteWaitHandler extends AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		final List<TaskContena> scheduledTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.SCHEDULED);
		final List<TaskContena> runningTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);

		if (scheduledTaskList.size() == 0 && runningTaskList.size() == 0) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					;
				}
			}
		}
	}

}

package org.seasar.chronos.core.handler.impl;

import java.util.concurrent.Future;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;

public class ScheduleExecuteExceptionHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskContenaStateManager.forEach(new TaskScheduleEntryHanlder() {

			public Object processTaskScheduleEntry(
					TaskScheduleEntry scheduleEntry) {
				Future<TaskExecutorService> taskExecutorServiceFuture = scheduleEntry
						.getTaskStaterFuture();

				return null;
			}
		});
	}

}

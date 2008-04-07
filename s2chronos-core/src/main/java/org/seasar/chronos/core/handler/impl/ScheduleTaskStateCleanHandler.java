package org.seasar.chronos.core.handler.impl;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;

// TODO アンスケジュールドなタスク状態を掃除します．
public class ScheduleTaskStateCleanHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskContenaStateManager.forEach(TaskStateType.UNSCHEDULED,
				new TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry scheduleEntry) {
						//scheduleEntry.getTaskExecutorService().getUnScheduledDateTime();
						return null;
					}
		});

	}

}

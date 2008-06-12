package org.seasar.chronos.core.handler.impl;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;

// TODO アンスケジュールドなタスク状態を掃除します．
public class ScheduleTaskStateCleanHandler extends
		AbstractScheduleExecuteHandler {

	private static int TIME_OUT = 60;

	@Override
	public void handleRequest() throws InterruptedException {
		TaskScheduleEntry taskScheduleEntry = (TaskScheduleEntry) this.taskContenaStateManager
				.forEach(TaskStateType.UNSCHEDULED,
						new TaskScheduleEntryHanlder() {
							public Object processTaskScheduleEntry(
									TaskScheduleEntry scheduleEntry) {
								long now = System.currentTimeMillis();
								if (scheduleEntry.getUnScheduledDate() != null
										&& now > TIME_OUT
												+ scheduleEntry
														.getUnScheduledDate()
														.getTime()) {
									return scheduleEntry;
								}
								return null;
							}
						});
		if (taskScheduleEntry != null) {
			taskContenaStateManager.removeTaskScheduleEntry(taskScheduleEntry);
		}

	}
}

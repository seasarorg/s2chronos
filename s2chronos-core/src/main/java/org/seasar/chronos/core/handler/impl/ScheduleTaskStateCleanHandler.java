/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
								if (scheduleEntry.getUnScheduledDate() != null) {
									if (now > TIME_OUT
											+ scheduleEntry
													.getUnScheduledDate()
													.getTime()) {
										return scheduleEntry;
									}
								}
								return null;
							}
						});
		if (taskScheduleEntry != null) {
			log.debug("UNSCHEDULEDなタスクを削除しました "
					+ taskScheduleEntry.getComponentDef().getComponentName());
			taskContenaStateManager.removeTaskScheduleEntry(taskScheduleEntry);
		}

	}
}

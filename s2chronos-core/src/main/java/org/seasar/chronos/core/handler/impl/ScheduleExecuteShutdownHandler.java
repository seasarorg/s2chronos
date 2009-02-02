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

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.seasar.chronos.core.impl.SchedulerImpl;
import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskStateType;
import org.seasar.chronos.core.model.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;

public class ScheduleExecuteShutdownHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskScheduleEntryManager.forEach(TaskStateType.RUNNING,
				new TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							final TaskScheduleEntry taskScheduleEntry) {
						final TaskExecutorService tes = taskScheduleEntry
								.getTaskExecutorService();
						if (tes.getTaskPropertyReader().isShutdownTask(false)) {
							log.log("DCHRONOS0011",
									new Object[] { tes.getTaskPropertyReader()
											.getTaskName(null) });
							final Future<TaskExecutorService> future = executorService
									.submit(new Callable<TaskExecutorService>() {
										public TaskExecutorService call()
												throws Exception {
											Object[] logArgs = new Object[] { tes
													.getTaskPropertyReader()
													.getTaskName(null) };
											log.log("DCHRONOS0002", logArgs);
											log.log("DCHRONOS0012", logArgs);
											if (tes.cancel()) {
												while (!tes
														.await(
																SchedulerImpl.SHUTDOWN_AWAIT_TIME,
																SchedulerImpl.SHUTDOWN_AWAIT_TIMEUNIT)) {
													log.log("DCHRONOS0013",
															logArgs);
												}
												schedulerEventHandler
														.fireCancelTask(tes
																.getTask());
												taskScheduleEntryManager
														.removeTaskScheduleEntry(
																TaskStateType.CANCELING,
																taskScheduleEntry);
												taskScheduleEntryManager
														.addTaskScheduleEntry(
																TaskStateType.UNSCHEDULED,
																taskScheduleEntry);
											} else {
												// TODO キャンセル失敗をワーニングでログを出力する
												log.debug("cancel error!");
											}
											log.log("DCHRONOS0014", logArgs);
											log
													.log(
															"DCHRONOS0003",
															new Object[] { tes
																	.getTaskPropertyReader()
																	.getTaskName(
																			null) });
											return tes;
										}
									});
							taskScheduleEntry.setTaskStaterFuture(future);
							// キャンセル中に登録する
							taskScheduleEntryManager.addTaskScheduleEntry(
									TaskStateType.CANCELING, taskScheduleEntry);
							taskScheduleEntryManager.removeTaskScheduleEntry(
									TaskStateType.RUNNING, taskScheduleEntry);

						}
						return null;
					}
				});
	}
}

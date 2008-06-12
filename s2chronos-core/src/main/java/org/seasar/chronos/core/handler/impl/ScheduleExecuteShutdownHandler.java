package org.seasar.chronos.core.handler.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.SchedulerImpl;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;

public class ScheduleExecuteShutdownHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskContenaStateManager.forEach(TaskStateType.RUNNING,
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
												taskContenaStateManager
														.removeTaskScheduleEntry(
																TaskStateType.CANCELING,
																taskScheduleEntry);
												taskContenaStateManager
														.addTaskScheduleEntry(
																TaskStateType.UNSCHEDULED,
																taskScheduleEntry);
											} else {
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
							taskContenaStateManager.addTaskScheduleEntry(
									TaskStateType.CANCELING, taskScheduleEntry);
							taskContenaStateManager.removeTaskScheduleEntry(
									TaskStateType.RUNNING, taskScheduleEntry);

						}
						return null;
					}
				});
	}
}

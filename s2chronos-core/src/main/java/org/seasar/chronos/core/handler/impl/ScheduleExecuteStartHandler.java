package org.seasar.chronos.core.handler.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.util.TaskPropertyUtil;

public class ScheduleExecuteStartHandler extends AbstractScheduleExecuteHandler {

	private String taskName;

	private void fireEndTaskEvent(TaskExecutorService tes) {
		if (schedulerEventHandler != null) {
			schedulerEventHandler.fireEndTask(tes.getTask());
		}
	}

	private void fireStartTaskEvent(TaskExecutorService tes) {
		if (schedulerEventHandler != null) {
			schedulerEventHandler.fireStartTask(tes.getTask());
		}
	}

	@Override
	public void handleRequest() throws InterruptedException {

		this.taskContenaStateManager.forEach(TaskStateType.SCHEDULED,
				new TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							final TaskScheduleEntry taskScheduleEntry) {
						final TaskExecutorService tes = taskScheduleEntry
								.getTaskExecutorService();
						if (TaskPropertyUtil.isStartTask(tes)) {
							log.log("DCHRONOSSSTHRT001",
									new Object[] { TaskPropertyUtil
											.getTaskName(tes) });

							Callable<TaskExecutorService> task = new Callable<TaskExecutorService>() {
								public TaskExecutorService call()
										throws Exception {
									log
											.debug("ADD RUNNING = "
													+ TaskPropertyUtil
															.getTaskId(taskScheduleEntry
																	.getTaskExecutorService()));
									taskContenaStateManager
											.addTaskScheduleEntry(
													TaskStateType.RUNNING,
													taskScheduleEntry);

									// 定期スケジュール以外ならスケジュールドリストから削除する
									if (!TaskPropertyUtil.isReSchedule(tes)) {
										log
												.debug("REMOVE SCHEDULED = "
														+ TaskPropertyUtil
																.getTaskId(taskScheduleEntry
																		.getTaskExecutorService()));
										taskContenaStateManager
												.removeTaskScheduleEntry(
														TaskStateType.SCHEDULED,
														taskScheduleEntry);

									}
									TaskExecutorService _tes = tes;
									taskName = TaskPropertyUtil
											.getTaskName(_tes);

									log.log("DCHRONOS000111",
											new Object[] { taskName });
									log.debug("fireStartTaskEvent(_tes);");
									fireStartTaskEvent(_tes);
									log
											.debug("String nextTaskName = _tes.initialize();");
									String nextTaskName = _tes.initialize();
									log
											.debug("taskExecute(_tes, nextTaskName);");
									taskExecute(_tes, nextTaskName);
									log.debug("nextTaskName = _tes.destroy();");
									nextTaskName = null;
									nextTaskName = _tes.destroy();
									log
											.debug("scheduleTask(_tes, nextTaskName);");
									scheduleTask(_tes, nextTaskName);
									log.debug("fireEndTaskEvent(_tes);");
									fireEndTaskEvent(_tes);

									log
											.debug("REMOVE RUNNING = "
													+ TaskPropertyUtil
															.getTaskId(taskScheduleEntry
																	.getTaskExecutorService()));
									taskContenaStateManager
											.removeTaskScheduleEntry(
													TaskStateType.RUNNING,
													taskScheduleEntry);

									// 定期スケジュール以外ならアンスケジュールドリストに登録する
									if (!TaskPropertyUtil.isReSchedule(tes)) {
										log
												.debug("ADD UNSCHEDULED = "
														+ TaskPropertyUtil
																.getTaskId(taskScheduleEntry
																		.getTaskExecutorService()));
										taskContenaStateManager
												.addTaskScheduleEntry(
														TaskStateType.UNSCHEDULED,
														taskScheduleEntry);

									}
									log.log("DCHRONOS000112",
											new Object[] { taskName });
									return tes;
								}

							};
							final Future<TaskExecutorService> taskStaterFuture = executorService
									.submit(task);

							taskScheduleEntry
									.setTaskStaterFuture(taskStaterFuture);

						}
						return null;
					}
				});
	}

	private void scheduleTask(TaskExecutorService tes, String nextTaskName) {
		if (nextTaskName != null) {
			Scheduler scheduler = tes.getScheduler();
			scheduler.addTask(nextTaskName);
		}
	}

	private void taskExecute(TaskExecutorService tes, String nextTaskName)
			throws InterruptedException {
		if (nextTaskName != null) {
			try {
				tes.execute(nextTaskName);
				tes.waitOne();
			} catch (RejectedExecutionException ex) {
				log.log("ECHRONOS0002", new Object[] { taskName }, ex);
			}
		}
	}

}

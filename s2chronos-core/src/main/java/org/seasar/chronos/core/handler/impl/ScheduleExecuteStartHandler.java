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
							Future<TaskExecutorService> taskStaterFuture = executorService
									.submit(new Callable<TaskExecutorService>() {
										public TaskExecutorService call()
												throws Exception {
											TaskExecutorService _tes = tes;
											taskName = TaskPropertyUtil
													.getTaskName(_tes);

											log.log("DCHRONOS000111",
													new Object[] { taskName });
											fireStartTaskEvent(_tes);
											String nextTaskName = _tes
													.initialize();
											taskExecute(_tes, nextTaskName);
											nextTaskName = null;
											nextTaskName = _tes.destroy();
											scheduleTask(_tes, nextTaskName);
											fireEndTaskEvent(_tes);
											taskContenaStateManager
													.removeTaskScheduleEntry(
															TaskStateType.RUNNING,
															taskScheduleEntry);

											if (TaskPropertyUtil
													.isReSchedule(tes)) {
												taskContenaStateManager
														.addTaskScheduleEntry(
																TaskStateType.SCHEDULED,
																taskScheduleEntry);
											} else {
												taskContenaStateManager
														.addTaskScheduleEntry(
																TaskStateType.UNSCHEDULED,
																taskScheduleEntry);
											}
											log.log("DCHRONOS000112",
													new Object[] { taskName });
											return tes;
										}

									});

							taskScheduleEntry
									.setTaskStaterFuture(taskStaterFuture);
							taskContenaStateManager.addTaskScheduleEntry(
									TaskStateType.RUNNING, taskScheduleEntry);
							taskContenaStateManager.removeTaskScheduleEntry(
									TaskStateType.SCHEDULED, taskScheduleEntry);
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

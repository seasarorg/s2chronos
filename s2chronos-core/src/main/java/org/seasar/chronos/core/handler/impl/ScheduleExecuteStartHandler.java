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
import org.seasar.framework.convention.NamingConvention;

public class ScheduleExecuteStartHandler extends AbstractScheduleExecuteHandler {

	private String taskName;

	private NamingConvention namingConvention;

	private void fireExceptionTaskEvent(TaskExecutorService tes, Exception e) {
		if (schedulerEventHandler != null) {
			schedulerEventHandler.fireExceptionTask(tes.getTask(), e);
		}
	}

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

						// ここでgetComponentされます。
						tes.prepare();

						Object task = tes.getTask();
						Class<?> taskClass = tes.getTaskClass();

						// 
						taskScheduleEntry.setTask(task);
						taskScheduleEntry.setTaskClass(taskClass);
						String[] rootPackageNames = namingConvention
								.getRootPackageNames();

						if (TaskPropertyUtil.isStartTask(tes, rootPackageNames)) {
							log.log("DCHRONOSSSTHRTSTT",
									new Object[] { TaskPropertyUtil
											.getTaskName(tes) });

							Callable<TaskExecutorService> TaskExecutorServiceCallable = new Callable<TaskExecutorService>() {
								public TaskExecutorService call()
										throws Exception {

									TaskExecutorService _tes = tes;
									taskContenaStateManager
											.addTaskScheduleEntry(
													TaskStateType.RUNNING,
													taskScheduleEntry);

									// 定期スケジュール以外ならスケジュールドリストから削除する
									if (!TaskPropertyUtil.isReSchedule(tes)) {
										taskContenaStateManager
												.removeTaskScheduleEntry(
														TaskStateType.SCHEDULED,
														taskScheduleEntry);

									}

									taskName = TaskPropertyUtil
											.getTaskName(_tes);

									log.log("DCHRONOS000111",
											new Object[] { taskName });

									try {
										fireStartTaskEvent(_tes);
										String nextTaskName = _tes.initialize();
										taskExecute(_tes, nextTaskName);
									} catch (Exception e) {
										fireExceptionTaskEvent(_tes, e);
									} finally {
										String nextTaskName = _tes.destroy();
										scheduleTask(_tes, nextTaskName);
										fireEndTaskEvent(_tes);
									}

									taskContenaStateManager
											.removeTaskScheduleEntry(
													TaskStateType.RUNNING,
													taskScheduleEntry);

									// 定期スケジュール以外ならアンスケジュールドリストに登録する
									if (!TaskPropertyUtil.isReSchedule(tes)) {

										taskContenaStateManager
												.addTaskScheduleEntry(
														TaskStateType.UNSCHEDULED,
														taskScheduleEntry);

									}
									log.log("DCHRONOS000112",
											new Object[] { taskName });

									tes.unprepare();

									taskScheduleEntry.setTask(null);
									taskScheduleEntry.setTaskClass(null);

									return tes;
								}

							};
							final Future<TaskExecutorService> taskStaterFuture = executorService
									.submit(TaskExecutorServiceCallable);

							taskScheduleEntry
									.setTaskStaterFuture(taskStaterFuture);

						} else {

							tes.unprepare();

							taskScheduleEntry.setTask(null);
							taskScheduleEntry.setTaskClass(null);

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

	public void setNamingConvention(NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

}

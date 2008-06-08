package org.seasar.chronos.core.handler.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;
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

	/**
	 * タスク実行サービス用のCallableです．
	 * 
	 * @author junichi
	 * 
	 */
	private class TaskExecutorServiceCallable implements
			Callable<TaskExecutorService> {

		private TaskExecutorService taskExecutorService;

		public void setTaskExecutorService(
				final TaskExecutorService taskExecutorService) {
			this.taskExecutorService = taskExecutorService;
		}

		private TaskScheduleEntry taskScheduleEntry;

		public void setTaskScheduleEntry(
				final TaskScheduleEntry taskScheduleEntry) {
			this.taskScheduleEntry = taskScheduleEntry;
		}

		public TaskExecutorService call() throws Exception {
			final String taskName = taskExecutorService.getTaskPropertyReader()
					.getTaskName(null);
			log.log("DCHRONOS0121", new Object[] { taskName });
			taskContenaStateManager.addTaskScheduleEntry(TaskStateType.RUNNING,
					taskScheduleEntry);
			// 定期スケジュール以外ならスケジュールドリストから削除する
			if (!taskExecutorService.getTaskPropertyReader()
					.isReSchedule(false)) {
				taskContenaStateManager.removeTaskScheduleEntry(
						TaskStateType.SCHEDULED, taskScheduleEntry);
			}
			try {
				fireStartTaskEvent(taskExecutorService);
				final String nextTaskName = taskExecutorService.initialize();
				taskExecute(taskExecutorService, nextTaskName);
			} catch (final Exception e) {
				fireExceptionTaskEvent(taskExecutorService, e);
			} finally {
				final String nextTaskName = taskExecutorService.destroy();
				// scheduleTask(taskExecutorService, nextTaskName);
				fireEndTaskEvent(taskExecutorService);
			}
			taskContenaStateManager.removeTaskScheduleEntry(
					TaskStateType.RUNNING, taskScheduleEntry);
			// 定期スケジュール以外ならアンスケジュールドリストに登録する
			// TODO アンスケジュールドに入った時刻をtseに持たせて，別のハンドラーで一定時間経過後にアンスケジュールドリストから削除する．
			if (!taskExecutorService.getTaskPropertyReader()
					.isReSchedule(false)) {
				taskContenaStateManager.addTaskScheduleEntry(
						TaskStateType.UNSCHEDULED, taskScheduleEntry);
			}
			log.log("DCHRONOSSSTHRTTCF", new Object[] { taskName });
			taskExecutorService.unprepare();
			taskScheduleEntry.setTask(null);
			taskScheduleEntry.setTaskClass(null);
			return taskExecutorService;
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
						// HOT deploy 開始
						tes.hotdeployStart();
						// タスクの準備ができていないなら
						if (!tes.isPrepared()) {
							tes.prepare();
							Object task = tes.getTask();
							Class<?> taskClass = tes.getTaskClass();
							taskScheduleEntry.setTask(task);
							taskScheduleEntry.setTaskClass(taskClass);
						}
						if (!tes.isExecuted()
								&& tes.getTaskPropertyReader().isStartTask(
										false)) {
							log.log("DCHRONOSSSTHRTSTT",
									new Object[] { tes.getTaskPropertyReader()
											.getTaskName(null) });
							TaskExecutorServiceCallable tesc = new TaskExecutorServiceCallable();
							tesc.setTaskExecutorService(tes);
							tesc.setTaskScheduleEntry(taskScheduleEntry);
							Future<TaskExecutorService> taskStaterFuture = executorService
									.submit(tesc);
							taskScheduleEntry
									.setTaskStaterFuture(taskStaterFuture);
						}
						// HOT deploy 終了
						tes.hotdeployStop();
						return null;
					}
				});
	}

	// private void scheduleTask(TaskExecutorService tes, String nextTaskName) {
	// if (nextTaskName != null) {
	// Scheduler scheduler = tes.getScheduler();
	// scheduler.addTask(nextTaskName);
	// }
	// }

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

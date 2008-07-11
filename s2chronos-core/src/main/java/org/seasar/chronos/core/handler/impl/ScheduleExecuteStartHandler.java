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
import java.util.concurrent.RejectedExecutionException;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;

public class ScheduleExecuteStartHandler extends AbstractScheduleExecuteHandler {

	private String taskName;

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
			log.log("DCHRONOS0122", new Object[] { taskName });
			taskScheduleEntryManager.addTaskScheduleEntry(TaskStateType.RUNNING,
					taskScheduleEntry);
			// 定期スケジュール以外ならスケジュールドリストから削除する
			if (!taskExecutorService.getTaskPropertyReader()
					.isReSchedule(false)) {
				taskScheduleEntryManager.removeTaskScheduleEntry(
						TaskStateType.SCHEDULED, taskScheduleEntry);
			}
			try {
				fireStartTaskEvent(taskExecutorService);
				final String nextTaskName = taskExecutorService.initialize();
				taskExecute(taskExecutorService, nextTaskName);
			} catch (final Exception e) {
				fireExceptionTaskEvent(taskExecutorService, e);
			} finally {
				@SuppressWarnings("unused")
				final String nextTaskName = taskExecutorService.destroy();
				// scheduleTask(taskExecutorService, nextTaskName);
				log.log("DCHRONOS0123", new Object[] { taskName });
				fireEndTaskEvent(taskExecutorService);
			}
			taskScheduleEntryManager.removeTaskScheduleEntry(
					TaskStateType.RUNNING, taskScheduleEntry);
			// 定期スケジュール以外ならアンスケジュールドリストに登録する
			// TODO アンスケジュールドに入った時刻をtseに持たせて，別のハンドラーで一定時間経過後にアンスケジュールドリストから削除する．
			if (!taskExecutorService.getTaskPropertyReader()
					.isReSchedule(false)) {
				taskScheduleEntryManager.addTaskScheduleEntry(
						TaskStateType.UNSCHEDULED, taskScheduleEntry);
			}
			taskExecutorService.unprepare();
			taskScheduleEntry.setTask(null);
			taskScheduleEntry.setTaskClass(null);
			log.log("DCHRONOS0124", new Object[] { taskName });
			return taskExecutorService;
		}

	}

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskScheduleEntryManager.forEach(TaskStateType.SCHEDULED,
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
						}
						if (!tes.isExecuted()
								&& tes.getTaskPropertyReader().isStartTask(
										false)) {
							Object task = tes.getTask();
							Class<?> taskClass = tes.getTaskClass();
							taskScheduleEntry.setTask(task);
							taskScheduleEntry.setTaskClass(taskClass);
							log.log("DCHRONOS0121",
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

}

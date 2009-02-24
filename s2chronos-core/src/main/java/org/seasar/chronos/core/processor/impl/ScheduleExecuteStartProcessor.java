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
package org.seasar.chronos.core.processor.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskStateType;
import org.seasar.chronos.core.model.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;

/**
 * タスクを開始するハンドラークラスです．
 * 
 * @author j5ik2o
 */
public class ScheduleExecuteStartProcessor extends AbstractScheduleExecuteProcessor {
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
	 * タスク実行サービス用の{@code Callable}です．
	 * 
	 * @author j5ik2o
	 */
	private class TaskExecutorServiceCallable implements
	        Callable<TaskExecutorService> {
		private TaskExecutorService taskExecutorService;

		/**
		 * {@link TaskExecutorService}を設定します。
		 * 
		 * @param taskExecutorService
		 *            {@link TaskExecutorService}
		 */
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
			final String taskName =
			    taskExecutorService.getTaskPropertyReader().getTaskName(null);
			log.log("DCHRONOS0122", new Object[] { taskName });
			taskScheduleEntryManager.addTaskScheduleEntry(
			    TaskStateType.RUNNING,
			    taskScheduleEntry);
			// 定期スケジュール以外ならスケジュールドリストから削除する
			if (!taskExecutorService.getTaskPropertyReader().isReScheduleTask(
			    false)) {
				taskScheduleEntryManager.removeTaskScheduleEntry(
				    TaskStateType.SCHEDULED,
				    taskScheduleEntry);
			}
			try {
				fireStartTaskEvent(taskExecutorService);
				final String nextTaskName = taskExecutorService.start();
				taskExecute(taskExecutorService, nextTaskName);
			} catch (final Exception e) {
				taskExecutorService.setException(e);
				taskExecutorService.catchException(e);
				fireExceptionTaskEvent(taskExecutorService, e);
			} finally {
				@SuppressWarnings("unused")
				final String nextTaskName = taskExecutorService.end();
				// scheduleTask(taskExecutorService, nextTaskName);
				log.log("DCHRONOS0123", new Object[] { taskName });
				fireEndTaskEvent(taskExecutorService);
			}
			taskScheduleEntryManager.removeTaskScheduleEntry(
			    TaskStateType.RUNNING,
			    taskScheduleEntry);
			// 定期スケジュール以外ならアンスケジュールドリストに登録する
			if (!taskExecutorService.getTaskPropertyReader().isReScheduleTask(
			    false)) {
				// タスク実行中にisReScheduleTaskが変更される場合があるので，その場合は再度SCHEDULEDを確認して存在すれば削除する．
				if (taskScheduleEntryManager.contains(
				    TaskStateType.SCHEDULED,
				    taskScheduleEntry)) {
					taskScheduleEntryManager.removeTaskScheduleEntry(
					    TaskStateType.SCHEDULED,
					    taskScheduleEntry);
				}
				taskScheduleEntryManager.addTaskScheduleEntry(
				    TaskStateType.UNSCHEDULED,
				    taskScheduleEntry);
			}
			taskExecutorService.unprepare();
			log.log("DCHRONOS0124", new Object[] { taskName });
			taskExecutorService.hotdeployStop();
			return taskExecutorService;
		}
	}

	/*
	 * (非 Javadoc)
	 * @seeorg.seasar.chronos.core.handler.impl.AbstractScheduleExecuteHandler#
	 * doProcess()
	 */
	@Override
	public void doProcess() throws InterruptedException {
		this.taskScheduleEntryManager.forEach(
		    TaskStateType.SCHEDULED,
		    new TaskScheduleEntryHanlder<Void>() {
			    public Void processTaskScheduleEntry(
			            final TaskScheduleEntry taskScheduleEntry) {
				    final TaskExecutorService tes =
				        taskScheduleEntry.getTaskExecutorService();
				    // HOT deploy 開始
				    tes.hotdeployStart();
				    // タスクの準備ができていないなら
				    if (!tes.isPrepared()) {
					    tes.prepare();
					    try {
						    tes.initialize();
					    } catch (InterruptedException e) {
						    ;
					    }
					    tes.hotdeployStop();
					    return null;
				    }
				    // log.debug("tes=" + tes);
				    // log.debug(tes.getTaskClass() + "=" + tes.getTask());
				    // BeanDesc bd =
				    // BeanDescFactory.getBeanDesc(tes.getTaskClass());
				    // for (int i = 0; i < bd.getPropertyDescSize(); i++) {
				    // PropertyDesc pd = bd.getPropertyDesc(i);
				    // if (pd.isReadable()) {
				    // log.debug("scheduler scan : "
				    // + tes.getTaskName()
				    // + ":"
				    // + pd.getPropertyName()
				    // + "="
				    // + pd.getValue(tes.getTask()));
				    // // propertyCache.put(pd.getPropertyName(), pd
				    // // .getValue(tes.getTask()));
				    // }
				    // }
				    if (!tes.isExecuting()
				        && tes.getTaskPropertyReader().isStartTask(false)) {
					    Object task = tes.getTask();
					    Class<?> taskClass = tes.getTaskClass();
					    taskScheduleEntry.setTask(task);
					    taskScheduleEntry.setTaskClass(taskClass);
					    log.log("DCHRONOS0121", new Object[] { tes
					        .getTaskPropertyReader()
					        .getTaskName(null) });
					    TaskExecutorServiceCallable tesc =
					        new TaskExecutorServiceCallable();
					    tesc.setTaskExecutorService(tes);
					    tesc.setTaskScheduleEntry(taskScheduleEntry);
					    Future<TaskExecutorService> taskStaterFuture =
					        executorService.submit(tesc);
					    taskScheduleEntry.setTaskStaterFuture(taskStaterFuture);
				    } else {
					    // HOT deploy 終了
					    tes.hotdeployStop();
				    }
				    return null;
			    }
		    });
	}

	private void taskExecute(TaskExecutorService tes, String nextTaskName)
	        throws InterruptedException {
		try {
			tes.execute(nextTaskName);
			tes.waitOne();
		} catch (RejectedExecutionException ex) {
			final String taskName =
			    tes.getTaskPropertyReader().getTaskName(null);
			log.log("ECHRONOS0002", new Object[] { taskName }, ex);
		}
	}
}

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
package org.seasar.chronos.core;

import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskStateType;

/**
 * スケジューライベントリスナークラスです．
 * 
 * @author j5ik2o
 */
public interface SchedulerEventListener {
	/**
	 * {@link TaskScheduleEntry}を追加したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 * @param taskStateType
	 *            {@link TaskStateType}
	 * @param taskScheduleEntry
	 *            {@link TaskScheduleEntry}
	 */
	public void addTaskScheduleEntry(Scheduler scheduler,
	        TaskStateType taskStateType, TaskScheduleEntry taskScheduleEntry);

	/**
	 * タスクがキャンセルされたときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            　{@link Scheduler}
	 * @param task
	 *            タスクのインスタンス
	 */
	public void cancelTask(Scheduler scheduler, Object task);

	/**
	 * スケジューラが終了したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void endScheduler(Scheduler scheduler);

	/**
	 * タスクが終了したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 * @param task
	 *            タスクのインスタンス
	 */
	public void endTask(Scheduler scheduler, Object task);

	/**
	 * スケジューラが一時停止したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void pauseScheduler(Scheduler scheduler);

	/**
	 * スケジューラが再開されたときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void resumeScheduler(Scheduler scheduler);

	/**
	 * タスクスケジュールエントリが削除されたときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 * @param taskStateType
	 *            {@link TaskStateType}
	 * @param taskScheduleEntry
	 *            {@link TaskScheduleEntry}
	 */
	public void removeTaskScheduleEntry(Scheduler scheduler,
	        TaskStateType taskStateType, TaskScheduleEntry taskScheduleEntry);

	/**
	 * スケジューラにタスクが登録された後に呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void registerTaskSchedulerAfter(Scheduler scheduler);

	/**
	 * スケジューラにタスクが登録された前に呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void registerTaskSchedulerBefore(Scheduler scheduler);

	/**
	 * スケジューラをシャットダウンするときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void shutdownScheduler(Scheduler scheduler);

	/**
	 * スケジューラが開始したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 */
	public void startScheduler(Scheduler scheduler);

	/**
	 * タスクが開始したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 * @param task
	 *            タスクのインスタンス
	 */
	public void startTask(Scheduler scheduler, Object task);

	/**
	 * タスクで例外が発生したときに呼ばれるハンドラーです．
	 * 
	 * @param scheduler
	 *            {@link Scheduler}
	 * @param task
	 *            タスクのインスタンス
	 * @param e
	 *            発生した例外
	 */
	public void exceptionTask(Scheduler scheduler, Object task, Exception e);
}

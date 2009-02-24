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
package org.seasar.chronos.core.model;

import java.io.Serializable;

/**
 * トリガーインターフェイスです．
 * 
 * @author j5ik2o
 */
public interface TaskTrigger extends Serializable {
	/**
	 * 概要を返します．
	 * 
	 * @return 概要
	 */
	public String getDescription();

	/**
	 * 名前を返します．
	 * 
	 * @return 名前
	 */
	public String getName();

	/**
	 * タスクのインスタンスを返します．
	 * 
	 * @return タスクのインスタンス
	 */
	public Object getTask();

	/**
	 * トリガーIDを返します．
	 * 
	 * @return トリガーID
	 */
	public Long getTriggerId();

	/**
	 * タスク終了フラグを返します．
	 * 
	 * @return タスク終了フラグ(true=終了する, false=終了しない)
	 */
	public boolean isEndTask();

	/**
	 * タスク実行済フラグを返します．
	 * 
	 * @return タスク実行済フラグ(true=実行済, false=まだ実行されていない)
	 */
	public boolean isExecuted();

	/**
	 * タスク実行中フラグを返します．
	 * 
	 * @return タスク実行中フラグ(true=タスクが実行中, false=タスクが実行中でない)
	 */
	public boolean isExecuting();

	/**
	 * 強制アンスケジュールフラグを返します．
	 * 
	 * @return 強制アンスケジュールフラグ(true=強制的にアンスケジュールする, false=強制的にアンスケジュールしない)
	 */
	public boolean isForceUnScheduleTask();

	/**
	 * 再スケジュールフラグを返します．
	 * 
	 * @return 再スケジュールフラグ (true=タスク実行後に再度スケジュールされる, false=再度スケジュールされない)
	 */
	public boolean isReScheduleTask();

	/**
	 * シャットダウンフラグを返します．
	 * 
	 * @return シャットダウンフラグ
	 */
	public boolean isShutdownTask();

	/**
	 * タスク開始フラグを返します．
	 * 
	 * @return タスク開始フラグ(true=タスクを開始する, false=タスクを開始しない)
	 */
	public boolean isStartTask();

	/**
	 * 概要を設定します．
	 * 
	 * @param description
	 *            概要
	 */
	public void setDescription(String description);

	/**
	 * タスク終了フラグを設定します．
	 * 
	 * @param endTask
	 *            タスク終了フラグ
	 */
	public void setEndTask(boolean endTask);

	/**
	 * タスク実行済フラグを設定します．
	 * 
	 * @param executed
	 *            タスク実行済フラグ
	 */
	public void setExecuted(boolean executed);

	public void setExecuting(boolean executing);

	public void setForceUnScheduleTask(boolean forceUnScheduleTask);

	public void setName(String name);

	public void setReScheduleTask(boolean reScheduleTask);

	public void setShutdownTask(boolean shutdownTask);

	public void setStartTask(boolean startTask);

	public void setTask(Object task);

	public void setTriggerId(Long id);
}

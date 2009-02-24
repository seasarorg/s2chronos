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
import java.util.Date;
import java.util.concurrent.Future;

import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

/**
 * タスクスケジュールエントリクラスです．
 * 
 * @author j5ik2o
 */
public interface TaskScheduleEntry extends Serializable {
	/**
	 * {@link ComponentDef}を返します．
	 * 
	 * @return　{@link ComponentDef}
	 */
	public ComponentDef getComponentDef();

	/**
	 * スケジュールIDを返します．
	 * 
	 * @return スケジュールID
	 */
	public Long getScheduleId();

	/**
	 * タスクのインスタンスを返します．
	 * 
	 * @return タスクのインスタンス
	 */
	public Object getTask();

	/**
	 * タスククラスを返します．
	 * 
	 * @return タスクのクラスを返します．
	 */
	public Class<?> getTaskClass();

	/**
	 * タスク実行サービスを返します．
	 * 
	 * @return {@link TaskExecutorService}
	 */
	public TaskExecutorService getTaskExecutorService();

	/**
	 * タスク実行サービスの{@link Future}を返します．
	 * 
	 * @return {@link Future}
	 */
	public Future<TaskExecutorService> getTaskStaterFuture();

	/**
	 * タスク状態を返します．
	 * 
	 * @return {@link TaskStateType}
	 */
	public TaskStateType getTaskStateType();

	/**
	 * {@link ComponentDef}を設定します．
	 * 
	 * @param componentDef
	 *            {@link ComponentDef}
	 */
	public void setComponentDef(ComponentDef componentDef);

	/**
	 * スケジュールIDを設定します．
	 * 
	 * @param scheduleId
	 *            スケジュールID
	 */
	public void setScheduleId(Long scheduleId);

	/**
	 * タスクのインスタンスを返します．
	 * 
	 * @param target
	 *            タスクのインスタンス
	 */
	public void setTask(Object target);

	/**
	 * タスククラスを設定します．
	 * 
	 * @param targetClass
	 *            タスククラス
	 */
	public void setTaskClass(Class<?> targetClass);

	/**
	 * タスク実行サービスを設定します．
	 * 
	 * @param taskExecutorService
	 *            {@link TaskExecutorService}
	 */
	public void setTaskExecutorService(TaskExecutorService taskExecutorService);

	/**
	 * タスク実行サービスの{@link Future}を設定します．
	 * 
	 * @param future
	 *            {@link Future}
	 */
	public void setTaskStaterFuture(Future<TaskExecutorService> future);

	/**
	 * タスク状態を返します．
	 * 
	 * @param taskStateType
	 *            {@link TaskStateType}
	 */
	public void setTaskStateType(TaskStateType taskStateType);

	/**
	 * アンスケジュールされた日時を返します．
	 * 
	 * @return アンスケジュールされた日時
	 */
	public Date getUnScheduledDate();

	/**
	 * 　 アンスケジュールされた日時を設定します．
	 * 
	 * @param date
	 *            　アンスケジュールされた日時
	 */
	public void setUnScheduledDate(Date date);
}

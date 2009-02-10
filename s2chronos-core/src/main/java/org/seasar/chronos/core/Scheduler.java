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

import org.seasar.chronos.core.model.SchedulerConfiguration;

/**
 * スケジューラのインターフェイスです。
 * 
 * @author j5ik2o
 */
public interface Scheduler {
	/**
	 * スケジューラ設定を返します。
	 * 
	 * @return スケジューラ設定
	 */
	public SchedulerConfiguration getSchedulerConfiguration();

	/**
	 * スケジューラ設定を設定します。
	 * 
	 * @param schedulerConfiguration
	 *            スケジューラ設定
	 */
	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration);

	/**
	 * スケジューラを開始します。
	 */
	public void start();

	/**
	 * スケジューラを一時停止します。
	 */
	public void pause();

	/**
	 * スケジューラが一時停止中かどうかを返します。
	 * 
	 * @return 一時停止中ならtrue、それ以外ならfalse
	 */
	public boolean isPaused();

	/**
	 * スケジューラをシャットダウンします。
	 */
	public void shutdown();

	/**
	 * スケジューラを待機します．
	 */
	public void join();

	/**
	 * スケジューラの起動と待機を行います。
	 */
	public void process();

	/**
	 * スケジューラにタスクを追加します。
	 * <p>
	 * タスクはS2管理下のコンポーネントでなければなりません。
	 * </p>
	 * 
	 * @param taskComponentClass
	 *            タスク
	 * @return 追加に成功した場合はtrue, 失敗した場合はfalse
	 */
	public boolean addTask(Class<?> taskComponentClass);

	/**
	 * スケジューラからタスクを削除します。
	 * 
	 * @param taskComponentClass
	 *            タスク
	 * @return 削除に成功した場合はtrue、失敗した場合はfalse
	 */
	public boolean removeTask(Class<?> taskComponentClass);

	/**
	 * スケジューラにリスナーを追加します。
	 * 
	 * @param listener
	 *            リスナー
	 * @return 追加に成功した場合はtrue, 失敗した場合はfalse
	 */
	public boolean addListener(SchedulerEventListener listener);

	/**
	 * スケジューラからリスナーを削除します。
	 * 
	 * @param listener
	 *            リスナー
	 * @return 削除に成功した場合はtrue、 失敗した場合はfalse
	 */
	public boolean removeListener(SchedulerEventListener listener);
}

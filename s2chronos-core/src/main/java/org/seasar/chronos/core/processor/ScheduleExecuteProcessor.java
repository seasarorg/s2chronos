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
package org.seasar.chronos.core.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.core.event.SchedulerEventHandler;

/**
 * スケジューラ実行ハンドラーインターフェイスです．
 * 
 * @author j5ik2o
 */
public interface ScheduleExecuteProcessor {
	public void setExecutorService(ExecutorService executorService);

	/**
	 * ハンドラーの処理を実行します．
	 * 
	 * @throws InterruptedException
	 */
	public void doProcess() throws InterruptedException;

	/**
	 * 一時停止フラグを設定します．
	 * 
	 * @param pause
	 *            一時停止フラグ
	 */
	public void setPause(AtomicBoolean pause);

	/**
	 * 一時停止完了フラグを設定します．
	 * 
	 * @param paused
	 *            一時停止完了フラグ
	 */
	public void setPaused(AtomicBoolean paused);

	/**
	 * @param schedulerEventHandler
	 */
	public void setSchedulerEventHandler(
	        SchedulerEventHandler schedulerEventHandler);
}

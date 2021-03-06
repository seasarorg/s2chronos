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

import org.seasar.chronos.core.model.TaskStateType;

/**
 * タスクを一時停止にするためのプロセッサクラスです．
 * 
 * @author j5ik2o
 */
public class ScheduleExecuteWaitProcessor extends
        AbstractScheduleExecuteProcessor {
	private Object pauseLock;

	private long waitInterval = 1000L;

	/*
	 * (非 Javadoc)
	 * @seeorg.seasar.chronos.core.handler.impl.AbstractScheduleExecuteHandler#
	 * doProcess()
	 */
	@Override
	public void doProcess() throws InterruptedException {
		// 一時停止命令があるか，スケジュールおよび実行中のタスクがなければ
		if (this.pause.get()
		    || taskScheduleEntryManager.size(TaskStateType.SCHEDULED) == 0
		    && taskScheduleEntryManager.size(TaskStateType.RUNNING) == 0) {
			synchronized (pauseLock) {
				try {
					do {
						if (this.paused != null) {
							// 一時停止命令があり一時停止中でなければ
							if (!this.paused.get() && this.pause.get()) {
								// 状態フラグを一時停止中にする
								this.paused.set(true);
								log.log("DCHRONOS0019", null);
								this.schedulerEventHandler.firePauseScheduler();
							}
						}
						pauseLock.wait(waitInterval);
					} while (this.pause.get()
					    && !(taskScheduleEntryManager
					        .size(TaskStateType.SCHEDULED) == 0));
				} catch (InterruptedException e) {
					throw e;
				} finally {
					if (this.paused != null) {
						if (this.paused.get()) {
							this.paused.set(false);
							log.log("DCHRONOS0021", null);
							this.schedulerEventHandler.fireResumeScheduler();
						}
					}
				}
			}
		}
	}

	/**
	 * 一時停止用オブジェクトを設定します．
	 * 
	 * @param pauseLock
	 *            一時停止用オブジェクト
	 */
	public void setPauseLock(Object pauseLock) {
		this.pauseLock = pauseLock;
	}

	/**
	 * 一時停止間隔を設定します．
	 * 
	 * @param waitInterval
	 *            一時停止間隔(msec)
	 */
	public void setPauseLockWaitInterval(long waitInterval) {
		this.waitInterval = waitInterval;
	}
}

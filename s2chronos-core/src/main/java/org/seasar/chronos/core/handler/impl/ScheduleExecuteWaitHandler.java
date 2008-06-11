package org.seasar.chronos.core.handler.impl;

import org.seasar.chronos.core.impl.TaskStateType;

public class ScheduleExecuteWaitHandler extends AbstractScheduleExecuteHandler {

	private Object pauseLock;

	private long waitInterval = 1000L;

	@Override
	public void handleRequest() throws InterruptedException {
		// 一時停止命令があるか，スケジュールおよび実行中のタスクがなければ
		if (this.pause.get()
				|| taskContenaStateManager.size(TaskStateType.SCHEDULED) == 0
				&& taskContenaStateManager.size(TaskStateType.RUNNING) == 0) {
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
					} while (this.pause.get() && !(taskContenaStateManager.size(TaskStateType.SCHEDULED) == 0));
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

	public void setPauseLock(Object pauseLock) {
		this.pauseLock = pauseLock;
	}

	public void setPauseLockWaitInterval(long waitInterval) {
		this.waitInterval = waitInterval;
	}

}

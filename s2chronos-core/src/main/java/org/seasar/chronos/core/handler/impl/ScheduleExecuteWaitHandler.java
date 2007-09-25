package org.seasar.chronos.core.handler.impl;

import org.seasar.chronos.core.impl.TaskStateType;

public class ScheduleExecuteWaitHandler extends AbstractScheduleExecuteHandler {

	private Object pauseLock;

	private long waitInterval = 1000L;

	@Override
	public void handleRequest() throws InterruptedException {
		if (this.pause.get()
				|| taskContenaStateManager.size(TaskStateType.SCHEDULED) == 0
				&& taskContenaStateManager.size(TaskStateType.RUNNING) == 0) {
			synchronized (pauseLock) {
				try {
					do {
						if (this.paused != null) {
							if (!this.paused.get() && this.pause.get()) {
								this.paused.set(true);
								this.schedulerEventHandler.firePauseScheduler();
							}
						}
						pauseLock.wait(waitInterval);
					} while (this.pause.get());
				} catch (InterruptedException e) {
					log.log("WCHNS0001", null, e);
					throw e;
				} finally {
					if (this.paused != null) {
						if (this.paused.get()) {
							this.paused.set(false);
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

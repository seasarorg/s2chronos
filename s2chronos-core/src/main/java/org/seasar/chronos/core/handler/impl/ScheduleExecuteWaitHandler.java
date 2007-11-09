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
						log.debug("handleRequest - 1");
						if (this.paused != null) {
							log.debug("handleRequest - 2");
							if (!this.paused.get() && this.pause.get()) {
								log.debug("handleRequest - 3");
								this.paused.set(true);
								log.debug("handleRequest - 4");
								this.schedulerEventHandler.firePauseScheduler();
								log.debug("handleRequest - 5");
							}
						}
						log.debug("wait start");
						pauseLock.wait(waitInterval);
						log.debug("wait end");
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

package org.seasar.chronos.handler.impl;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.impl.TaskStateType;

public class ScheduleExecuteWaitHandler extends AbstractScheduleExecuteHandler {

	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void handleRequest() throws InterruptedException {
		if (this.pause.get()
				|| taskContenaStateManager.size(TaskStateType.SCHEDULED) == 0
				&& taskContenaStateManager.size(TaskStateType.RUNNING) == 0) {
			synchronized (scheduler) {
				log.debug("scheduler.wait start");
				try {
					do {
						scheduler.wait(1000);
					} while (this.pause.get());
				} catch (InterruptedException e) {
					log.log("WCHNS0001", null, e);
					throw e;
				}
				log.debug("scheduler.wait end");
			}
		}
	}

}

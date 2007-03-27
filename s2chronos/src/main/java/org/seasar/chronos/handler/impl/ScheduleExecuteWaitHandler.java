package org.seasar.chronos.handler.impl;

import java.util.List;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskStateType;

public class ScheduleExecuteWaitHandler extends AbstractScheduleExecuteHandler {

	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void handleRequest() throws InterruptedException {
		final List<TaskContena> scheduledTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.SCHEDULED);
		final List<TaskContena> runningTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);
		if (this.pause.get() || scheduledTaskList.size() == 0
				&& runningTaskList.size() == 0) {
			synchronized (scheduler) {
				log.debug("scheduler.wait start");
				try {
					do {
						scheduler.wait();
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

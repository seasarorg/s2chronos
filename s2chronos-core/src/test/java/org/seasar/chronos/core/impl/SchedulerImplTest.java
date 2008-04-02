package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.task.NoScheduleTask;
import org.seasar.framework.log.Logger;

public class SchedulerImplTest extends S2TestCaseBase implements
		SchedulerEventListener {

	private transient static Logger log = Logger
			.getLogger(SchedulerImplTest.class);

	private Scheduler scheduler;

	public void testStart() {
		scheduler.addListener(this);
		scheduler.start();
		scheduler.join();

	}

	public void testAddTask() {
		scheduler.addTask(NoScheduleTask.class);
		scheduler.addListener(this);
		scheduler.start();
		scheduler.join();
	}

	public void testRemoveTask() {
		scheduler.addTask(NoScheduleTask.class);
		scheduler.removeTask(NoScheduleTask.class);
		scheduler.addListener(this);
		scheduler.start();
		scheduler.join();
	}

	public void testPause() {
		scheduler.addTask(NoScheduleTask.class);
		scheduler.removeTask(NoScheduleTask.class);
		scheduler.addListener(this);
		scheduler.start();
		scheduler.pause();
		try {
			Thread.sleep(2200);
		} catch (InterruptedException e) {
		}
		scheduler.pause();
		scheduler.join();
	}

	public void addTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry) {

	}

	public void cancelTask(Scheduler scheduler, Object task) {

	}

	public void endScheduler(Scheduler scheduler) {

	}

	public void endTask(Scheduler scheduler, Object task) {

	}

	public void exceptionTask(Scheduler scheduler, Object task, Exception ex) {
		log.debug("exception", ex);
	}

	public void pauseScheduler(Scheduler scheduler) {

	}

	public void resumeScheduler(Scheduler scheduler) {

	}

	public void removeTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry) {

	}

	public void resigtTaskAfterScheduler(Scheduler scheduler) {

	}

	public void resigtTaskBeforeScheduler(Scheduler scheduler) {

	}

	public void shutdownScheduler(Scheduler scheduler) {

	}

	public void startScheduler(Scheduler scheduler) {

	}

	public void startTask(Scheduler scheduler, Object task) {

	}

}

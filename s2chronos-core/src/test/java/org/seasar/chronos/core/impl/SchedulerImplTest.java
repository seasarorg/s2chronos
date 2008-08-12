package org.seasar.chronos.core.impl;

import org.junit.runner.RunWith;
import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.test.task.NoScheduleTask;
import org.seasar.framework.log.Logger;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class SchedulerImplTest implements
		SchedulerEventListener {

	private transient static Logger log = Logger
			.getLogger(SchedulerImplTest.class);

	private Scheduler scheduler;

	public void testStart() {
		this.scheduler.addListener(this);
		this.scheduler.start();
		this.scheduler.join();
	}
//
//	public void testAddTask() {
//		this.scheduler.addTask(NoScheduleTask.class);
//		this.scheduler.addListener(this);
//		this.scheduler.start();
//		this.scheduler.join();
//	}
//
//	public void testRemoveTask() {
//		this.scheduler.addTask(NoScheduleTask.class);
//		this.scheduler.removeTask(NoScheduleTask.class);
//		this.scheduler.addListener(this);
//		this.scheduler.start();
//		this.scheduler.join();
//	}
//
//	public void testPause() {
//		this.scheduler.addTask(NoScheduleTask.class);
//		this.scheduler.removeTask(NoScheduleTask.class);
//		this.scheduler.addListener(this);
//		this.scheduler.start();
//		this.scheduler.pause();
//		try {
//			Thread.sleep(2200);
//		} catch (InterruptedException e) {
//		}
//		this.scheduler.pause();
//		this.scheduler.join();
//	}

	public void addTaskScheduleEntry(Scheduler scheduler,TaskStateType taskStateType,
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
			TaskStateType taskStateType, TaskScheduleEntry taskScheduleEntry) {		
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

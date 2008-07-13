package org.seasar.chronos.core.event;

import junit.framework.TestCase;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.SchedulerImpl;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.ScheduleEntry;

public class SchedulerEventHandlerTest extends TestCase implements
		SchedulerEventListener {

	private SchedulerEventHandler schedulerEventHandler;

	public void addTaskScheduleEntry(Scheduler scheduler,TaskStateType taskStateType,
			TaskScheduleEntry taskScheduleEntry) {
		System.out.println("addTaskScheduleEntry");
	}

	public void cancelTask(Scheduler scheduler, Object task) {
		System.out.println("cancelTask");
	}

	public void endScheduler(Scheduler scheduler) {
		System.out.println("endScheduler");
	}

	public void endTask(Scheduler scheduler, Object task) {
		System.out.println("endTask");
	}

	public void pauseScheduler(Scheduler scheduler) {
		System.out.println("pauseScheduler");
	}

	public void resumeScheduler(Scheduler scheduler) {
		System.out.println("resumeScheduler");
	}

	public void removeTaskScheduleEntry(Scheduler scheduler,TaskStateType taskStateType,
			TaskScheduleEntry taskScheduleEntry) {
		System.out.println("removeTaskScheduleEntry");
	}

	public void resigtTaskAfterScheduler(Scheduler scheduler) {
		System.out.println("resigtTaskAfterScheduler");
	}

	public void resigtTaskBeforeScheduler(Scheduler scheduler) {
		System.out.println("resigtTaskBeforeScheduler");
	}

	public void exceptionTask(Scheduler scheduler, Object task, Exception e) {
		System.out.println("exceptionTask");
	}

	@Override
	protected void setUp() throws Exception {
		schedulerEventHandler = new SchedulerEventHandler();
		schedulerEventHandler.setScheduler(new SchedulerImpl());
		schedulerEventHandler.add(this);
		super.setUp();
	}

	public void shutdownScheduler(Scheduler scheduler) {
		System.out.println("shutdownScheduler");
	}

	public void startScheduler(Scheduler scheduler) {
		System.out.println("startScheduler");
	}

	public void startTask(Scheduler scheduler, Object task) {
		System.out.println("startTask");
	}

	public void testFireAddTask() {
		TaskScheduleEntry taskScheduleEntry = new ScheduleEntry();
		schedulerEventHandler.fireAddTaskScheduleEntry(TaskStateType.SCHEDULED, taskScheduleEntry);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireCancelTask() {
		schedulerEventHandler.fireCancelTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireEndScheduler() {
		schedulerEventHandler.fireEndScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireEndTask() {
		schedulerEventHandler.fireEndTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFirePauseScheduler() {
		schedulerEventHandler.firePauseScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireRegistTaskAfterScheduler() {
		schedulerEventHandler.fireRegisterTaskAfterScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireRegistTaskBeforeScheduler() {
		schedulerEventHandler.fireRegisterTaskBeforeScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireShutdownScheduler() {
		schedulerEventHandler.fireShutdownScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireStartScheduler() {
		schedulerEventHandler.fireStartScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireStartTask() {
		schedulerEventHandler.fireStartTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireExceptionTask() {
		schedulerEventHandler.fireExceptionTask(this, new Exception());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

}

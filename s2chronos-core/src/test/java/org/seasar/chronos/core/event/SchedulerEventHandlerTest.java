package org.seasar.chronos.core.event;

import junit.framework.TestCase;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.impl.SchedulerImpl;

public class SchedulerEventHandlerTest extends TestCase implements
		SchedulerEventListener {

	private SchedulerEventHandler target;

	public void addTask(Scheduler scheduler, Object task) {
		System.out.println("addTask");
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

	public void resigtTaskAfterScheduler(Scheduler scheduler) {
		System.out.println("resigtTaskAfterScheduler");
	}

	public void resigtTaskBeforeScheduler(Scheduler scheduler) {
		System.out.println("resigtTaskBeforeScheduler");
	}

	@Override
	protected void setUp() throws Exception {
		target = new SchedulerEventHandler(new SchedulerImpl());
		target.add(this);
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
		target.fireAddTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireCancelTask() {
		target.fireCancelTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireEndScheduler() {
		target.fireEndScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireEndTask() {
		target.fireEndTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFirePauseScheduler() {
		target.firePauseScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireRegistTaskAfterScheduler() {
		target.fireRegistTaskAfterScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireRegistTaskBeforeScheduler() {
		target.fireRegistTaskBeforeScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireShutdownScheduler() {
		target.fireShutdownScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireStartScheduler() {
		target.fireStartScheduler();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

	public void testFireStartTask() {
		target.fireStartTask(this);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
	}

}

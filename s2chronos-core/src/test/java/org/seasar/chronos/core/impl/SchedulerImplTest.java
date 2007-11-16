package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.task.SimpleTask;
import org.seasar.framework.log.Logger;

public class SchedulerImplTest extends S2TestCaseBase implements
		SchedulerEventListener {
	private transient static Logger log = Logger
			.getLogger(SchedulerImplTest.class);

	public void testStart() {

		Scheduler scheduler = (Scheduler) this.getComponent("scheduler");
		scheduler.addTask(SimpleTask.class);
		scheduler.addListener(this);
		scheduler.start();
		scheduler.join();

	}

	public void addTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void cancelTask(Scheduler scheduler, Object task) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void endScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void endTask(Scheduler scheduler, Object task) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void exceptionTask(Scheduler scheduler, Object task, Exception ex) {
		log.debug("exception", ex);
	}

	public void pauseScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void resigtTaskAfterScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void resigtTaskBeforeScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void shutdownScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void startScheduler(Scheduler scheduler) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void startTask(Scheduler scheduler, Object task) {
		// TODO 自動生成されたメソッド・スタブ

	}

}

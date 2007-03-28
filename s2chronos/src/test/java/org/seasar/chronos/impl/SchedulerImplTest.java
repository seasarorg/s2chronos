package org.seasar.chronos.impl;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.test.task.SmartTask;
import org.seasar.extension.unit.S2TestCase;

public class SchedulerImplTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private Scheduler scheduler;

	private SmartTask task;

	protected void setUp() throws Exception {
		include(PATH);
		scheduler = (Scheduler) this.getComponent(Scheduler.class);
		task = (SmartTask) this.getComponent(SmartTask.class);
	}

	public void testStart() {
		try {
			scheduler.start();
			scheduler.join();
		} catch (InterruptedException e) {
			;
		}
	}

}

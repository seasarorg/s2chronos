package org.seasar.chronos.impl;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.Scheduler;

public class SchedulerImplTest extends S2TestCaseBase {

	public void testStart() {

		Scheduler scheduler = (Scheduler) this.getComponent("scheduler");
		scheduler.start();
		scheduler.join();
	}

}

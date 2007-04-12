package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.Scheduler;

public class SchedulerImplTest extends S2TestCaseBase {

	public void testStart() {

		Scheduler scheduler = (Scheduler) this.getComponent("scheduler");
		scheduler.start();
		scheduler.join();

	}

}

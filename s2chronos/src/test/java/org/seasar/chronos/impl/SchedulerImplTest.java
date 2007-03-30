package org.seasar.chronos.impl;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.Scheduler;

public class SchedulerImplTest extends S2TestCaseBase {

	private Scheduler scheduler;

	public void testStart() {
		this.scheduler.start();
		this.scheduler.join();
	}

}

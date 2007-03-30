package org.seasar.chronos.impl;

import org.seasar.chronos.Scheduler;
import org.seasar.extension.unit.S2TestCase;

public class SchedulerImplTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private Scheduler scheduler;

	protected void setUp() throws Exception {
		include(PATH);
		scheduler = (Scheduler) this.getComponent(Scheduler.class);
	}

	public void testStart() {
		scheduler.start();
		scheduler.join();
	}

}

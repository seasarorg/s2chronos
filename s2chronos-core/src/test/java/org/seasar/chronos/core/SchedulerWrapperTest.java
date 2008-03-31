package org.seasar.chronos.core;

import org.seasar.chronos.core.impl.SchedulerImpl;

public class SchedulerWrapperTest extends S2TestCaseBase {

	@Override
	protected void setUp() throws Exception {
		schedulerWrapper = new SchedulerWrapper(new SchedulerImpl()) {

			@Override
			protected void registTaskFromS2Container() {

			}

		};
		super.setUp();
	}

	private SchedulerWrapper schedulerWrapper;

	public void testGetSchedulerConfiguration() {
		SchedulerConfiguration config = schedulerWrapper
				.getSchedulerConfiguration();
		assertNotNull(config);
	}

	public void testIsPaused() {
		
	}

	public void testJoin() {
		
	}

	public void testPause() {
		
	}

	public void testShutdown() {
		
	}

	public void testStart() {
		
	}

}

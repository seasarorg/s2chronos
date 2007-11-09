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
		fail("�܂�������Ă��܂���B");
	}

	public void testJoin() {
		fail("�܂�������Ă��܂���B");
	}

	public void testPause() {
		fail("�܂�������Ă��܂���B");
	}

	public void testShutdown() {
		fail("�܂�������Ă��܂���B");
	}

	public void testStart() {
		fail("�܂�������Ă��܂���B");
	}

}

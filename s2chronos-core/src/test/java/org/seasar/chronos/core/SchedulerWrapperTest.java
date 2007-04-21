package org.seasar.chronos.core;

import org.seasar.chronos.core.impl.SchedulerImpl;

public class SchedulerWrapperTest extends S2TestCaseBase {

	@Override
	protected void setUp() throws Exception {
		schedulerWrapper = new SchedulerWrapper(new SchedulerImpl()) {

			@Override
			protected void registTaskFromS2Container() {
				// TODO 自動生成されたメソッド・スタブ

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
		fail("まだ実装されていません。");
	}

	public void testJoin() {
		fail("まだ実装されていません。");
	}

	public void testPause() {
		fail("まだ実装されていません。");
	}

	public void testShutdown() {
		fail("まだ実装されていません。");
	}

	public void testStart() {
		fail("まだ実装されていません。");
	}

}

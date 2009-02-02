package org.seasar.chronos.core;

import org.junit.runner.RunWith;
import org.seasar.chronos.core.impl.SchedulerImpl;
import org.seasar.chronos.core.model.SchedulerConfiguration;
import org.seasar.framework.unit.Seasar2;
import static org.junit.Assert.*;

@RunWith(Seasar2.class)
public class SchedulerWrapperTest  {


	public void postBindFields()  {
		schedulerWrapper = new SchedulerWrapper(new SchedulerImpl()) {

			@Override
			protected void registerTaskFromS2Container() {

			}

		};
	}

	private SchedulerWrapper schedulerWrapper;

	public void testGetSchedulerConfiguration() {
		SchedulerConfiguration config = schedulerWrapper
				.getSchedulerConfiguration();
		assertNotNull(config);
	}

	public void testIsPaused() {
		this.schedulerWrapper.isPaused();
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

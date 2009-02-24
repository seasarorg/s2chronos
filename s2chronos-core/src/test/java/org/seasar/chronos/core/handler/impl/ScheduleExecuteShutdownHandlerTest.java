package org.seasar.chronos.core.handler.impl;

import org.junit.runner.RunWith;
import org.seasar.chronos.core.processor.impl.ScheduleExecuteShutdownProcessor;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ScheduleExecuteShutdownHandlerTest {

	private ScheduleExecuteShutdownProcessor scheduleExecuteShutdownHandler;

	public void testHandleRequest() throws InterruptedException {

		scheduleExecuteShutdownHandler.doProcess();

	}

}

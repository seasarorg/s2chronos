package org.seasar.chronos.core.handler.impl;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class ScheduleExecuteShutdownHandlerTest {

	private ScheduleExecuteShutdownHandler scheduleExecuteShutdownHandler;

	public void testHandleRequest() throws InterruptedException {

		scheduleExecuteShutdownHandler.handleRequest();

	}

}

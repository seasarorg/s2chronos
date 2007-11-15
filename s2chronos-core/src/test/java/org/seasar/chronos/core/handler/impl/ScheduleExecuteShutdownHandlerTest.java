package org.seasar.chronos.core.handler.impl;

import org.seasar.chronos.core.S2TestCaseBase;

public class ScheduleExecuteShutdownHandlerTest extends S2TestCaseBase {

	private ScheduleExecuteShutdownHandler scheduleExecuteShutdownHandler;

	public void testHandleRequest() throws InterruptedException {

		scheduleExecuteShutdownHandler.handleRequest();

	}

}

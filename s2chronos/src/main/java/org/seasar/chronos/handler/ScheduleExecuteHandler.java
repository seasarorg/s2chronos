package org.seasar.chronos.handler;

import java.util.concurrent.ExecutorService;

public interface ScheduleExecuteHandler {

	public void setExecutorService(ExecutorService executorService);

	public void handleRequest() throws InterruptedException;

}

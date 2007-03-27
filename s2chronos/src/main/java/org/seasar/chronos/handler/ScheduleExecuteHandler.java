package org.seasar.chronos.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public interface ScheduleExecuteHandler {

	public void setExecutorService(ExecutorService executorService);

	public void handleRequest() throws InterruptedException;

	public void setPause(AtomicBoolean pause);

}

package org.seasar.chronos.job.impl;

import org.seasar.chronos.job.TaskExecuteHandler;

public abstract class AbstractTaskExecuteHandler implements TaskExecuteHandler {

	public abstract void handleRequest(String startTaskName);
}

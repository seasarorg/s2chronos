package org.seasar.chronos.job;

public interface TaskExecuteHandler {
	public void handleRequest(String startTaskName);
}

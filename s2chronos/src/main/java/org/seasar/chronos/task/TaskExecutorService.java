package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskExecutorService {

	public String initialize(ComponentDef job) throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy() throws InterruptedException;

	public boolean canExecute() throws InterruptedException;

}
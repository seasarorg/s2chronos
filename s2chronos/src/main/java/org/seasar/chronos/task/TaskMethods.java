package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskMethods {

	public void setTaskComponentDef(ComponentDef taskComponentDef);

	public void prepare();

	public String initialize() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy() throws InterruptedException;

}

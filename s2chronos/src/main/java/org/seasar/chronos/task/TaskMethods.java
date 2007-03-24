package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskMethods {

	public void setTaskComponentDef(ComponentDef taskComponentDef);

	public void setGetterSignal(Object getterSignal);

	public void prepare();

	public String initialize() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void waitOne() throws InterruptedException;

	public boolean cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy() throws InterruptedException;

}

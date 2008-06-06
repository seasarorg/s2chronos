package org.seasar.chronos.core.task;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface TaskMethods {

	public void setGetterSignal(Object getterSignal);

	public void hotdeployStart();

	public void hotdeployStop();

	public void prepare();

	public void unprepare();

	public String initialize() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void waitOne() throws InterruptedException;

	public boolean cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public String destroy() throws InterruptedException;

	public void setComponentDef(ComponentDef componentDef);

	public TaskPropertyReader getTaskPropertyReader();

	public TaskPropertyWriter getTaskPropertyWriter();
}

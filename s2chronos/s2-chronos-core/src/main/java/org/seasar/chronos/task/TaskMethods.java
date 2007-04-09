package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

public interface TaskMethods {

	public void setGetterSignal(Object getterSignal);

	public void prepare();

	public String initialize() throws InterruptedException;

	public void execute(String startTaskName) throws InterruptedException;

	public void waitOne() throws InterruptedException;

	public boolean cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public String destroy() throws InterruptedException;

}

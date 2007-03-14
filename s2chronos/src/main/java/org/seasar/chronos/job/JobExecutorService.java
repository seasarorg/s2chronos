package org.seasar.chronos.job;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public interface JobExecutorService {

	public String initialize(ComponentDef job) throws InterruptedException;

	public void execute(String startJobName) throws InterruptedException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy() throws InterruptedException;

	public boolean canExecute() throws InterruptedException;

}
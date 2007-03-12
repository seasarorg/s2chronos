package org.seasar.chronos.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.framework.container.ComponentDef;

public interface JobExecutorService {

	public String initialize(ComponentDef job) throws Throwable;

	public void callJob(String startJobName) throws InterruptedException,
			InvalidNextJobMethodException, ExecutionException;

	public void cancel();

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy() throws Throwable;

	public boolean canExecute() throws InterruptedException, ExecutionException;

}
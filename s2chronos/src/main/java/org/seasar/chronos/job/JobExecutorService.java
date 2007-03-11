package org.seasar.chronos.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.framework.container.ComponentDef;

public interface JobExecutorService {

	public abstract String initialize() throws InterruptedException, ExecutionException;

	public abstract void callJob(String startJobName)
			throws InterruptedException, InvalidNextJobMethodException,
			ExecutionException;

	public abstract void cancel() throws InterruptedException, ExecutionException;

	public abstract boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public abstract void destroy() throws InterruptedException, ExecutionException;

	public abstract boolean canExecute() throws InterruptedException, ExecutionException;

	public abstract void setJobComponentDef(ComponentDef job);

}
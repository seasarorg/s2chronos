package org.seasar.s2chronos.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.s2chronos.exception.InvalidNextJobMethodException;

public interface JobExecutorService {

	public abstract String initialize();

	public abstract void callJob(String startJobName)
			throws InterruptedException, InvalidNextJobMethodException,
			ExecutionException;

	public abstract void cancel();

	public abstract boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException;

	public abstract void destroy();

	public abstract boolean canExecute();

	public abstract void setJob(Object job);

}
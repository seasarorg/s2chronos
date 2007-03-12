package org.seasar.chronos.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.framework.container.ComponentDef;

public interface JobExecuteState {

	public String initialize(JobExecuteContext context,
			ComponentDef jobComponentDef) throws InterruptedException;

	public void callJob(JobExecuteContext context, String startJobName)
			throws InterruptedException, InvalidNextJobMethodException,
			ExecutionException;

	public void cancel(JobExecuteContext context);

	public boolean await(JobExecuteContext context, long time, TimeUnit timeUnit)
			throws InterruptedException;

	public void destroy(JobExecuteContext context) throws InterruptedException;

	public boolean canExecute(JobExecuteContext context)
			throws InterruptedException, ExecutionException;

}

package org.seasar.chronos.job.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.chronos.job.JobExecuteContext;
import org.seasar.framework.container.ComponentDef;

public class JobExecuteStateInitialized extends AbstractJobExecuteState {

	@Override
	public boolean await(JobExecuteContext context, long time, TimeUnit timeUnit)
			throws InterruptedException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void callJob(JobExecuteContext context, String startJobName)
			throws InterruptedException, InvalidNextJobMethodException,
			ExecutionException {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public boolean canExecute(JobExecuteContext context)
			throws InterruptedException, ExecutionException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public void cancel(JobExecuteContext context) {
		this.getJobExecuteStrategy().cancel();
		this.changeState(context, new JobExecuteStateNoInitialized());
	}

	@Override
	public void destroy(JobExecuteContext context) throws Throwable {
		this.getJobExecuteStrategy().destroy();
		this.changeState(context, new JobExecuteStateNoInitialized());
	}

	@Override
	public String initialize(JobExecuteContext context,
			ComponentDef jobComponentDef) throws Throwable {
		return null;
	}

}

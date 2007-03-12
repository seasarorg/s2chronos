package org.seasar.chronos.job;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.exception.InvalidNextJobMethodException;
import org.seasar.framework.container.ComponentDef;

public class JobExecuteContext {

	JobExecuteState currentState;

	public void changeState(JobExecuteState nextState) {
		this.currentState = nextState;
	}

	public String initialize(ComponentDef jobComponentDef) throws InterruptedException {
		return this.currentState.initialize(this, jobComponentDef);
	}

	public void callJob(String startJobName) throws InterruptedException,
			InvalidNextJobMethodException, ExecutionException {
		this.currentState.callJob(this, startJobName);
	}

	public void cancel() {
		this.currentState.cancel(this);
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.currentState.await(this, time, timeUnit);
	}

	public void destroy() throws InterruptedException {
		this.currentState.destroy(this);
	}

	public boolean canExecute() throws InterruptedException, ExecutionException {
		return this.currentState.canExecute(this);
	}

}

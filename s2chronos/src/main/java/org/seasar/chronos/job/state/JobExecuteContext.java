package org.seasar.chronos.job.state;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public class JobExecuteContext {

	JobExecuteState currentState;

	public void changeState(JobExecuteState nextState) {
		this.currentState = nextState;
	}

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {
		return this.currentState.initialize(this, jobComponentDef);
	}

	public void execute(String startJobName) throws InterruptedException {
		this.currentState.execute(this, startJobName);
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

	public boolean canExecute() throws InterruptedException {
		return this.currentState.canExecute(this);
	}

}

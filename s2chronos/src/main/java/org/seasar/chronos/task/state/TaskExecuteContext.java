package org.seasar.chronos.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.container.ComponentDef;

public class TaskExecuteContext {

	TaskExecuteState currentState;

	public void changeState(TaskExecuteState nextState) {
		this.currentState = nextState;
	}

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {
		return this.currentState.initialize(this, jobComponentDef);
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.currentState.execute(this, startTaskName);
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

}

package org.seasar.chronos.job;

public class Transition {

	private boolean processResult = false;

	private String nextTaskName = null;

	public Transition() {

	}

	public Transition(boolean processResult, String nextTaskName) {
		this.processResult = processResult;
		this.nextTaskName = nextTaskName;
	}

	public Transition(boolean processResult) {
		this.processResult = processResult;
	}

	public String getNextTaskName() {
		return nextTaskName;
	}

	public void setNextTaskName(String nextTaskName) {
		this.nextTaskName = nextTaskName;
	}

	public boolean isProcessResult() {
		return processResult;
	}

	public void setProcessResult(boolean processResult) {
		this.processResult = processResult;
	}
}

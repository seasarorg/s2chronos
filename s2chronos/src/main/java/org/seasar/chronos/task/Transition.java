package org.seasar.chronos.task;

public class Transition {

	private boolean processResult = false;

	private String nextTaskName = null;

	private String lastTaskName = null;

	public Transition() {

	}

	public Transition(boolean processResult, String nextTaskName,
			String lastTaskName) {
		this(processResult, nextTaskName);
		this.lastTaskName = lastTaskName;
	}

	public Transition(boolean processResult, String nextTaskName) {
		this(processResult);
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

	public String getLastTaskName() {
		return lastTaskName;
	}

	public void setLastTaskName(String lastTaskName) {
		this.lastTaskName = lastTaskName;
	}
}

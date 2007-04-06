package org.seasar.chronos.store.entity;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "TASK")
public class TaskEntity {

	private int taskId;

	private int triggerId;

	private String taskName;

	private boolean execute;

	private int threadPoolSize;

	private int threadPoolType;

	private boolean startTask;

	private boolean endTask;

	private boolean shutdownTask;

	public boolean isEndTask() {
		return endTask;
	}

	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	public boolean isExecute() {
		return execute;
	}

	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	public boolean isShutdownTask() {
		return shutdownTask;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	public boolean isStartTask() {
		return startTask;
	}

	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public int getThreadPoolType() {
		return threadPoolType;
	}

	public void setThreadPoolType(int threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public int getTriggerId() {
		return triggerId;
	}

	public void setTriggerId(int triggerId) {
		this.triggerId = triggerId;
	}

}

package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;

@Task
@NonDelayTrigger
public class TestTask {

	private Long taskId;
	private String taskName;
	private boolean reSchedule;
	private boolean executed;
	private boolean shutdownTask;
	private boolean startTask;
	private boolean endTask;
	private String description;
	private ThreadPoolType threadPoolType;
	private int threadPoolSize;
	private TaskTrigger trigger;

	public ThreadPoolType getThreadPoolType() {
		return this.threadPoolType;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public int getThreadPoolSize() {
		return this.threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isShutdownTask() {
		return this.shutdownTask;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	public boolean isStartTask() {
		return this.startTask;
	}

	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	public boolean isEndTask() {
		return this.endTask;
	}

	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	public boolean isExecuted() {
		return this.executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public boolean isReSchedule() {
		return this.reSchedule;
	}

	public void setReSchedule(boolean reSchedule) {
		this.reSchedule = reSchedule;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public TaskTrigger getTrigger() {
		return this.trigger;
	}

	public void setTrigger(TaskTrigger trigger) {
		this.trigger = trigger;
	}

}

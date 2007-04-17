package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;

@Bean(table = "TASK")
public class TaskEntity {

	private int taskId;

	private int triggerId;

	private String taskName;

	private boolean execute;

	private Integer threadPoolId;

	private int threadPoolSize;

	private int threadPoolType;

	private boolean startTask;

	private boolean endTask;

	private boolean shutdownTask;

	private BigDecimal versionNo;

	public int getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public int getThreadPoolId() {
		return threadPoolId;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public int getThreadPoolType() {
		return threadPoolType;
	}

	public Integer getTriggerId() {
		return triggerId;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public boolean isEndTask() {
		return endTask;
	}

	public boolean isExecute() {
		return execute;
	}

	public boolean isShutdownTask() {
		return shutdownTask;
	}

	public boolean isStartTask() {
		return startTask;
	}

	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	public void setExecute(boolean execute) {
		this.execute = execute;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	@Column("ID")
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setThreadPoolId(Integer threadPoolId) {
		this.threadPoolId = threadPoolId;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(int threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public void setTriggerId(int triggerId) {
		this.triggerId = triggerId;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

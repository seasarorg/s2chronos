package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "TASK")
public class TaskEntity {

	private Long id;

	private Integer taskCode;

	private Long triggerId;

	private String taskName;

	private boolean execute;

	private Long threadPoolId;

	private int threadPoolSize;

	private int threadPoolType;

	private boolean startTask;

	private boolean endTask;

	private boolean shutdownTask;

	private BigDecimal versionNo;

	public Long getId() {
		return id;
	}

	public Integer getTaskCode() {
		return taskCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public Long getThreadPoolId() {
		return threadPoolId;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public int getThreadPoolType() {
		return threadPoolType;
	}

	public Long getTriggerId() {
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

	@Id(value = IdType.SEQUENCE, sequenceName = "TASK_ID_SEQ")
	public void setId(Long id) {
		this.id = id;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	@Arguments("TASK_CODE")
	public void setTaskCode(Integer taskCode) {
		this.taskCode = taskCode;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setThreadPoolId(Long threadPoolId) {
		this.threadPoolId = threadPoolId;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(int threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public void setTriggerId(Long triggerId) {
		this.triggerId = triggerId;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

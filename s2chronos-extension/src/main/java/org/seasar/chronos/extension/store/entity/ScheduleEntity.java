package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "SCHEDULE")
public class ScheduleEntity {

	private Long id;

	private Integer schedulerCode;

	private String taskName;

	private String description;

	private Integer taskId;

	private Integer status;

	private Timestamp createDate;

	private Timestamp updateDate;

	private BigDecimal versionNo;

	public String getTaskName() {
		return taskName;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getSchedulerCode() {
		return schedulerCode;
	}

	public void setSchedulerCode(Integer schedulerCode) {
		this.schedulerCode = schedulerCode;
	}

}

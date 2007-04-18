package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

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

	public Timestamp getCreateDate() {
		return createDate;
	}

	public String getDescription() {
		return description;
	}

	public Long getId() {
		return id;
	}

	public Integer getSchedulerCode() {
		return schedulerCode;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Id(value = IdType.SEQUENCE, sequenceName = "SCHEDULE_ID_SEQ")
	public void setId(Long id) {
		this.id = id;
	}

	public void setSchedulerCode(Integer schedulerCode) {
		this.schedulerCode = schedulerCode;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

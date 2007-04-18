package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "TRIGGER")
public class TriggerEntity {

	private Long id;

	private Long objectId;

	private String name;

	private String description;

	private Boolean executed;

	private String execType;

	private Long delayTime;

	private String cronExpression;

	private Timestamp startTime;

	private Timestamp endTime;

	private BigDecimal versionNo;

	public String getCronExpression() {
		return cronExpression;
	}

	public Long getDelayTime() {
		return delayTime;
	}

	public String getDescription() {
		return description;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public String getExecType() {
		return execType;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public Long getObjectId() {
		return objectId;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public Boolean isExecuted() {
		return executed;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndTime(Timestamp endDate) {
		this.endTime = endDate;
	}

	public void setExecType(String execType) {
		this.execType = execType;
	}

	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}

	@Id(value = IdType.IDENTITY)
	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTime(Timestamp startDate) {
		this.startTime = startDate;
	}

	public void setObjectId(Long triggerCode) {
		this.objectId = triggerCode;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

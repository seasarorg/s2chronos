package org.seasar.chronos.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "TRIGGER")
public class TriggerEntity {

	private long id;

	private String name;

	private String description;

	private boolean executed;

	private String execType;

	private Long delayTime;

	private String cronExpression;

	private Timestamp startDate;

	private Timestamp endDate;

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

	public Timestamp getEndDate() {
		return endDate;
	}

	public String getExecType() {
		return execType;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public boolean isExecuted() {
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

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public void setExecType(String execType) {
		this.execType = execType;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

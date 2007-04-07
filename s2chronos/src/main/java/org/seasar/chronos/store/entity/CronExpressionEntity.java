package org.seasar.chronos.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class CronExpressionEntity {

	private Long id;

	private Timestamp startTime;

	private BigDecimal versionNo;

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long cronTriggerId) {
		this.id = cronTriggerId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

}

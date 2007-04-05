package org.seasar.chronos.store.entity;

import java.sql.Timestamp;

public class CronExpressionEntity {

	private Long id;

	private Timestamp startTime;

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

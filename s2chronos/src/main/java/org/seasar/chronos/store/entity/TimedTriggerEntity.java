package org.seasar.chronos.store.entity;

import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "TIMED_TRIGGER")
public class TimedTriggerEntity extends TriggerBase {

	private Timestamp startDate;

	private Timestamp endDate;

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

}

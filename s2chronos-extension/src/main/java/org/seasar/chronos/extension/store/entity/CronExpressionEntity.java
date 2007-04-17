package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "CRON_EXPRESSION")
public class CronExpressionEntity {

	private Long id;

	private Integer cronExpressionCode;

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

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Integer getCronExpressionCode() {
		return cronExpressionCode;
	}

	public void setCronExpressionCode(Integer cronExpressionCode) {
		this.cronExpressionCode = cronExpressionCode;
	}

}

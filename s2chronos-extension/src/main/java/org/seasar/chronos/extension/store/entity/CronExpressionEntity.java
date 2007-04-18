package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "CRON_EXPRESSION")
public class CronExpressionEntity {

	private Long id;

	private Long objectId;

	private Timestamp startTime;

	private BigDecimal versionNo;

	public Long getObjectId() {
		return objectId;
	}

	public Long getId() {
		return id;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setObjectId(Long cronExpressionCode) {
		this.objectId = cronExpressionCode;
	}

	@Id(value = IdType.IDENTITY)
	public void setId(Long id) {
		this.id = id;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

}

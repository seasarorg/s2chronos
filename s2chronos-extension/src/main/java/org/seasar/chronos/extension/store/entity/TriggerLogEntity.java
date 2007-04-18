package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "TRIGGER_LOG")
public class TriggerLogEntity {

	private Long id;

	private Long objectId;

	private BigDecimal versionNo;

	public Long getId() {
		return id;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	@Id(value = IdType.IDENTITY)
	public void setId(Long id) {
		this.id = id;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
}

package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "THREAD_POOL")
public class ThreadPoolEntity {

	private Long id;

	private Long objectId;

	private Integer threadPoolSize;

	private Integer threadPoolType;

	private BigDecimal versionNo;

	public Long getId() {
		return id;
	}

	public Long getObjectId() {
		return objectId;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public Integer getThreadPoolType() {
		return threadPoolType;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	@Id(value = IdType.IDENTITY)
	public void setId(Long id) {
		this.id = id;
	}

	public void setObjectId(Long threadPoolCode) {
		this.objectId = threadPoolCode;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(Integer threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}
}

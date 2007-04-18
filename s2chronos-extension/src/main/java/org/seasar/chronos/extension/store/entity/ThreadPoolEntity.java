package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Id;
import org.seasar.dao.annotation.tiger.IdType;

@Bean(table = "THREAD_POOL")
public class ThreadPoolEntity {

	private Long id;

	private Integer threadPoolCode;

	private Integer threadPoolSize;

	private Integer threadPoolType;

	private BigDecimal versionNo;

	public Long getId() {
		return id;
	}

	public Integer getThreadPoolCode() {
		return threadPoolCode;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public Integer getThreadPoolType() {
		return threadPoolType;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	@Id(value = IdType.SEQUENCE, sequenceName = "THREAD_POOL_ID_SEQ")
	public void setId(Long id) {
		this.id = id;
	}

	public void setThreadPoolCode(Integer threadPoolCode) {
		this.threadPoolCode = threadPoolCode;
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

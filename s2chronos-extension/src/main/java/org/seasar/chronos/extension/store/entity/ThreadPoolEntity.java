package org.seasar.chronos.extension.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;
import org.seasar.dao.annotation.tiger.Column;

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

	@Column("ID")
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(Integer threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public Integer getThreadPoolType() {
		return threadPoolType;
	}

	public void setThreadPoolType(Integer threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getThreadPoolCode() {
		return threadPoolCode;
	}

	public void setThreadPoolCode(Integer threadPoolCode) {
		this.threadPoolCode = threadPoolCode;
	}
}

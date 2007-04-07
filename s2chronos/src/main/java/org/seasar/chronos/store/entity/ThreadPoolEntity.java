package org.seasar.chronos.store.entity;

import java.math.BigDecimal;

import org.seasar.dao.annotation.tiger.Bean;

@Bean(table = "THREAD_POOL")
public class ThreadPoolEntity {

	private int threadPoolId;

	private int threadPoolSize;

	private int threadPoolType;

	private BigDecimal versionNo;

	public int getThreadPoolId() {
		return threadPoolId;
	}

	public void setThreadPoolId(int threadPoolId) {
		this.threadPoolId = threadPoolId;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public int getThreadPoolType() {
		return threadPoolType;
	}

	public void setThreadPoolType(int threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}
}

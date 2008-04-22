package org.seasar.chronos.core;

/**
 * スケジューラ設定クラスです．
 * 
 * @author junichi
 * 
 */
public final class SchedulerConfiguration {

	private boolean autoFinish;

	private long autoFinishTimeLimit;

	private long taskScanIntervalTime;

	private int threadPoolSize;

	private ThreadPoolType threadPoolType;

	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

	public long getAutoFinishTimeLimit() {
		return autoFinishTimeLimit;
	}

	public void setAutoFinishTimeLimit(long zeroScheduleTime) {
		this.autoFinishTimeLimit = zeroScheduleTime;
	}

	public long getTaskScanIntervalTime() {
		return taskScanIntervalTime;
	}

	public void setTaskScanIntervalTime(long taskScanIntervalTime) {
		this.taskScanIntervalTime = taskScanIntervalTime;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public ThreadPoolType getThreadPoolType() {
		return this.threadPoolType;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}
}

package org.seasar.chronos.core;

public final class SchedulerConfiguration {

	private boolean autoFinish;

	private long autoFinishTimeLimit;

	private long taskScanIntervalTime;

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

}

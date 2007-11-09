package org.seasar.chronos.core;

public final class SchedulerConfiguration {

	private boolean autoFinish;

	private long zeroScheduleTime;

	private long taskScanIntervalTime;

	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

	public long getZeroScheduleTime() {
		return zeroScheduleTime;
	}

	public void setZeroScheduleTime(long zeroScheduleTime) {
		this.zeroScheduleTime = zeroScheduleTime;
	}

	public long getTaskScanIntervalTime() {
		return taskScanIntervalTime;
	}

	public void setTaskScanIntervalTime(long taskScanIntervalTime) {
		this.taskScanIntervalTime = taskScanIntervalTime;
	}

}

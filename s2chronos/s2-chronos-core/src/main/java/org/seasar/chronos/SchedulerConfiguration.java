package org.seasar.chronos;

/**
 * スケジューラの設定を管理します.
 * 
 * @author junichi
 * 
 */
public final class SchedulerConfiguration {

	private boolean autoFinish;

	private int zeroScheduleTime;

	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

	public int getZeroScheduleTime() {
		return zeroScheduleTime;
	}

	public void setZeroScheduleTime(int zeroScheduleTime) {
		this.zeroScheduleTime = zeroScheduleTime;
	}

}

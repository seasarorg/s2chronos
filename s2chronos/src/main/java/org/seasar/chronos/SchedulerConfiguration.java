package org.seasar.chronos;

/**
 * スケジューラの設定を管理します.
 * 
 * @author junichi
 * 
 */
public final class SchedulerConfiguration {

	private boolean autoFinish;

	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

}

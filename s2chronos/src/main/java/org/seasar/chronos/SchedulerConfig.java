package org.seasar.chronos;

/**
 * スケジューラの設定を管理します.
 * 
 * @author junichi
 * 
 */
public final class SchedulerConfig {

	private boolean autoFinish;

	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

}

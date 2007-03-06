package org.seasar.s2chronos;

/**
 * スケジューラの設定を管理します.
 * 
 * @author junichi
 * 
 */
public final class SchedulerConfig {

	private boolean autoShutdownWithFinshedAllJob;

	public boolean isAutoShutdownWithFinshedAllJob() {
		return autoShutdownWithFinshedAllJob;
	}

	public void setAutoShutdownWithFinshedAllJob(
			boolean autoShutdownWithFinshedAllJob) {
		this.autoShutdownWithFinshedAllJob = autoShutdownWithFinshedAllJob;
	}

}

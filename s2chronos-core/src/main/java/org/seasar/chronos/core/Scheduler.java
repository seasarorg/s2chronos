package org.seasar.chronos.core;

public interface Scheduler {

	/**
	 * スケジューラの設定を返します．
	 * 
	 * @return スケジューラの設定
	 */
	public SchedulerConfiguration getSchedulerConfiguration();

	/**
	 * スケジューラの設定を設定します．
	 * 
	 * @param schedulerConfiguration
	 *            スケジューラの設定
	 */
	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration);

	/**
	 * スケジューラをスタートします．
	 * 
	 */
	public void start();

	/**
	 * スケジューラを一時停止します．
	 * 
	 */
	public void pause();

	public boolean isPaused();

	/**
	 * スケジューラをシャットダウンします．
	 * 
	 */
	public void shutdown();

	/**
	 * スケジューラを待機します．
	 * 
	 */
	public void join();

	public boolean addTask(String taskName);

	public void addTask(Class componentClass);

	public boolean removeTask(Object task);

	public boolean addListener(SchedulerEventListener listener);

	public boolean removeListener(SchedulerEventListener listener);

}
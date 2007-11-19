package org.seasar.chronos.core;

/**
 * スケジューラのインターフェイスです．
 * 
 * @author junichi
 * 
 */
public interface Scheduler {

	public SchedulerConfiguration getSchedulerConfiguration();

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration);

	public void start();

	public void pause();

	public boolean isPaused();

	public void shutdown();

	public void join();

	public boolean addTask(String taskName);

	/**
	 * スケジューラにコンポーネントを登録します．
	 * 
	 * @param taskComponentClass
	 */
	public boolean addTask(Class<?> taskComponentClass);

	public boolean removeTask(Class<?> taskComponentClass);

	public boolean addListener(SchedulerEventListener listener);

	public boolean removeListener(SchedulerEventListener listener);

}
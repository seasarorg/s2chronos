package org.seasar.chronos;

public interface Scheduler {

	public SchedulerConfiguration getSchedulerConfiguration();

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration);

	public void start();

	public void pause();

	public void shutdown();

	public void join();

	public void addTask(Class componentClass);

	public void addListener(SchedulerEventListener listener);

	public void removeListener(SchedulerEventListener listener);

	public void addTask(String taskName);

}
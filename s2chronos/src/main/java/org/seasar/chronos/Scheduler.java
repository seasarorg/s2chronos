package org.seasar.chronos;

public interface Scheduler {

	public SchedulerConfiguration getSchedulerConfiguration();

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration);

	public void start();

	public void pause();

	public void shutdown() throws InterruptedException;

	public void join() throws InterruptedException;

	public void addListener(SchedulerEventListener listener);

	public void removeListener(SchedulerEventListener listener);

}
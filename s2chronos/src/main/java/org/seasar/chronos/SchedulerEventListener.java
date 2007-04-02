package org.seasar.chronos;

public interface SchedulerEventListener {

	public void preparedScheduler(Scheduler scheduler);

	public void startScheduler(Scheduler scheduler);

	public void pauseScheduler(Scheduler scheduler);

	public void endScheduler(Scheduler scheduler);

	public void shutdownScheduler(Scheduler scheduler);

	public void addTask(Scheduler scheduler, Object task);

	public void startTask(Scheduler scheduler, Object task);

	public void endTask(Scheduler scheduler, Object task);

	public void cancelTask(Scheduler scheduler, Object task);

}

package org.seasar.chronos;

public interface SchedulerEventListener {

	public void startScheduler(Scheduler scheduler);

	public void shutdownScheduler(Scheduler scheduler);

	public void startJob(Scheduler scheduler, Object job);

	public void endJob(Scheduler scheduler, Object job);

	public void cancelJob(Scheduler scheduler, Object job);

}

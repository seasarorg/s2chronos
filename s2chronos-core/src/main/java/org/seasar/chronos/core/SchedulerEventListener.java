package org.seasar.chronos.core;

public interface SchedulerEventListener {

	public void addTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry);

	public void cancelTask(Scheduler scheduler, Object task);

	public void endScheduler(Scheduler scheduler);

	public void endTask(Scheduler scheduler, Object task);

	public void pauseScheduler(Scheduler scheduler);

	public void resumeScheduler(Scheduler scheduler);

	public void removeTaskScheduleEntry(Scheduler scheduler,
			TaskScheduleEntry taskScheduleEntry);

	public void resigtTaskAfterScheduler(Scheduler scheduler);

	public void resigtTaskBeforeScheduler(Scheduler scheduler);

	public void shutdownScheduler(Scheduler scheduler);

	public void startScheduler(Scheduler scheduler);

	public void startTask(Scheduler scheduler, Object task);

	public void exceptionTask(Scheduler scheduler, Object task, Exception e);

}

package org.seasar.chronos.core;

import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;

public interface SchedulerEventListener {

	public void addTask(Scheduler scheduler, TaskStateType type,
			TaskExecutorService taskExecutorService);

	public void cancelTask(Scheduler scheduler, Object task);

	public void endScheduler(Scheduler scheduler);

	public void endTask(Scheduler scheduler, Object task);

	public void pauseScheduler(Scheduler scheduler);

	public void removeTask(Scheduler scheduler, TaskStateType type,
			TaskExecutorService taskExecutorService);

	public void resigtTaskAfterScheduler(Scheduler scheduler);

	public void resigtTaskBeforeScheduler(Scheduler scheduler);

	public void shutdownScheduler(Scheduler scheduler);

	public void startScheduler(Scheduler scheduler);

	public void startTask(Scheduler scheduler, Object task);

}

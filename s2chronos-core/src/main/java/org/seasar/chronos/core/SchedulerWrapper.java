package org.seasar.chronos.core;

import org.seasar.chronos.core.impl.AbstractScheduler;

public abstract class SchedulerWrapper extends AbstractScheduler {

	private final Scheduler scheduler;

	public SchedulerWrapper(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean addListener(SchedulerEventListener listener) {
		return scheduler.addListener(listener);
	}

	public boolean addTask(String taskName) {
		return scheduler.addTask(taskName);
	}

	public void addTask(Class componentClass) {
		scheduler.addTask(componentClass);
	}

	public boolean removeTask(Object task) {
		return scheduler.removeTask(task);
	}

	public SchedulerConfiguration getSchedulerConfiguration() {
		return scheduler.getSchedulerConfiguration();
	}

	public boolean isPaused() {
		return scheduler.isPaused();
	}

	public void join() {
		scheduler.join();
	}

	public void pause() {
		scheduler.pause();
	}

	public boolean removeListener(SchedulerEventListener listener) {
		return scheduler.removeListener(listener);
	}

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration) {
		scheduler.setSchedulerConfiguration(schedulerConfiguration);
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public void start() {
		this.scheduler.start();
	}

}
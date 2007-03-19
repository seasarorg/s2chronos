package org.seasar.chronos;

import org.seasar.chronos.exception.SchedulerException;

public interface Scheduler {

	public SchedulerConfig getConfig();

	public void setConfig(SchedulerConfig config);

	public void start() throws SchedulerException;

	public void pause() throws SchedulerException;

	public void shutdown() throws InterruptedException;

	public void shutdown(boolean waitAllJobFinish) throws InterruptedException;

	public void join() throws InterruptedException;

	public void addListener(SchedulerEventListener listener);

	public void removeListener(SchedulerEventListener listener);

	public boolean addTask(Object task);

	public boolean removeTask(Object task);

}
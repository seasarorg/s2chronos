package org.seasar.chronos;

import org.seasar.chronos.exception.SchedulerException;
import org.seasar.chronos.trigger.Trigger;

public interface Scheduler {

	public SchedulerConfig getConfig();

	public void setConfig(SchedulerConfig config);

	public void start() throws SchedulerException;

	public void pause() throws SchedulerException;

	public void shutdown() throws SchedulerException;

	public void shutdown(boolean waitAllJobFinish) throws SchedulerException;

	public void join() throws SchedulerException;

	public void addListener(SchedulerEventListener listener);

	public void removeListener(SchedulerEventListener listener);

	public boolean addTriggerr(Trigger trigger) throws SchedulerException;

	public boolean removeTrigger(Trigger trigger) throws SchedulerException;

}
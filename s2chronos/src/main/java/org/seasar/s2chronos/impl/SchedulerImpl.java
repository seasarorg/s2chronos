package org.seasar.s2chronos.impl;

import org.seasar.s2chronos.Scheduler;
import org.seasar.s2chronos.SchedulerConfig;
import org.seasar.s2chronos.SchedulerEventListener;
import org.seasar.s2chronos.exception.SchedulerException;
import org.seasar.s2chronos.trigger.Trigger;

public class SchedulerImpl implements Scheduler {

	public void addListener(SchedulerEventListener listener) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean addTriggerr(Trigger trigger) throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public SchedulerConfig getConfig() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public void join() throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void pause() throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void removeListener(SchedulerEventListener listener) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public boolean removeTrigger(Trigger trigger) throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void setConfig(SchedulerConfig config) {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void shutdown() throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void shutdown(boolean waitAllJobFinish) throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ

	}

	public void start() throws SchedulerException {
		// TODO 自動生成されたメソッド・スタブ

	}

}

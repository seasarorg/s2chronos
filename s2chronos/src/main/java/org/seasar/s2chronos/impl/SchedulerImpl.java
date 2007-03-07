package org.seasar.s2chronos.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.seasar.s2chronos.Scheduler;
import org.seasar.s2chronos.SchedulerConfig;
import org.seasar.s2chronos.SchedulerEventListener;
import org.seasar.s2chronos.exception.SchedulerException;
import org.seasar.s2chronos.trigger.Trigger;

public class SchedulerImpl implements Scheduler {

	private ExecutorService executorService = Executors
			.newSingleThreadExecutor();

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
		executorService.shutdown();
		try {
			executorService.awaitTermination(3600, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			throw new SchedulerException(e);
		}
	}

	public void start() throws SchedulerException {

		Future<Void> future = executorService.submit(new Callable<Void>() {

			public Void call() throws Exception {
				// TODO 自動生成されたメソッド・スタブ
				return null;
			}

		});
	}

}

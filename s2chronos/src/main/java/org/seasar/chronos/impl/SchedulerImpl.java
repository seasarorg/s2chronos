package org.seasar.chronos.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerConfig;
import org.seasar.chronos.SchedulerEventListener;
import org.seasar.chronos.exception.SchedulerException;

public class SchedulerImpl implements Scheduler {

	private ExecutorService executorService = Executors
			.newSingleThreadExecutor();

	private Future<Void> future;

	private List<TaskContena> taskContenaList = Collections
			.synchronizedList(new ArrayList<TaskContena>());

	public void addListener(SchedulerEventListener listener) {

	}

	public SchedulerConfig getConfig() {
		return null;
	}

	public void join() throws InterruptedException {
		try {
			future.get();
		} catch (ExecutionException e) {
			;
		}
	}

	public void pause() throws SchedulerException {

	}

	public void removeListener(SchedulerEventListener listener) {

	}

	public void setConfig(SchedulerConfig config) {

	}

	public void shutdown() throws SchedulerException {

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

		// コンテナからタスクを取りだす

		future = executorService.submit(new Callable<Void>() {

			public Void call() throws Exception {
				if (taskContenaList.size() == 0) {
					wait();
				}
				// 起動できるタスクを探す
				for (TaskContena taskContena : taskContenaList) {

				}
				return null;
			}

		});
	}

	public boolean addTask(Object task) {
		TaskContena tc = new TaskContena();
		tc.setTarget(task);
		tc.setTargetClass(task.getClass());
		boolean ret = taskContenaList.add(tc);
		this.notify();
		return ret;
	}

	public boolean removeTask(Object task) {
		boolean ret = taskContenaList.remove(task);
		this.notify();
		return ret;
	}

}

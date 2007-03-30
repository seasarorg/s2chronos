package org.seasar.chronos.event;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerEventListener;

public class SchedulerEventHandler {

	private ExecutorService ExecutorService = Executors.newCachedThreadPool();

	private CopyOnWriteArrayList<SchedulerEventListener> schedulerEventListener = new CopyOnWriteArrayList<SchedulerEventListener>();

	private final Scheduler scheduler;

	public SchedulerEventHandler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean add(SchedulerEventListener listener) {
		return this.schedulerEventListener.add(listener);
	}

	public boolean remove(SchedulerEventListener listener) {
		return this.schedulerEventListener.remove(listener);
	}

	public void fireStartScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.startScheduler(scheduler);
				}
			});
		}
	}

	public void fireEndScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.endScheduler(scheduler);
				}
			});
		}
	}

	public void fireShutdownScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.shutdownScheduler(scheduler);
				}
			});
		}
	}

	public void fireStartTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.startTask(scheduler, task);
				}
			});
		}
	}

	public void fireEndTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.endTask(scheduler, task);
				}
			});
		}
	}

	public void fireCancelTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			ExecutorService.submit(new Runnable() {
				public void run() {
					listener.cancelTask(scheduler, task);
				}
			});
		}
	}
}

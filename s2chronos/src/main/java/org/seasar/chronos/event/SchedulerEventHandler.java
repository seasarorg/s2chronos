package org.seasar.chronos.event;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerEventListener;

public class SchedulerEventHandler {

	private ExecutorService ExecutorService = Executors.newCachedThreadPool();

	private CopyOnWriteArrayList<SchedulerEventListener> schedulerEventListener = new CopyOnWriteArrayList<SchedulerEventListener>();

	private boolean async;

	private final Scheduler scheduler;

	public SchedulerEventHandler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public boolean add(SchedulerEventListener listener) {
		return this.schedulerEventListener.add(listener);
	}

	public boolean remove(SchedulerEventListener listener) {
		return this.schedulerEventListener.remove(listener);
	}

	public void firePreparedScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.preparedScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireStartScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.startScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	private void waitFuture(Future<?> future) {
		if (!async) {
			try {
				future.get();
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩ catch ÉuÉçÉbÉN
				e.printStackTrace();
			}
		}
	}

	public void firePauseScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.pauseScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireEndScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.endScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireShutdownScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.shutdownScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireAddTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.addTask(scheduler, task);
				}
			});
			waitFuture(future);
		}
	}

	public void fireStartTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.startTask(scheduler, task);
				}
			});
			waitFuture(future);
		}
	}

	public void fireEndTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.endTask(scheduler, task);
				}
			});
			waitFuture(future);
		}
	}

	public void fireCancelTask(final Object task) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.cancelTask(scheduler, task);
				}
			});
			waitFuture(future);
		}
	}
}

package org.seasar.chronos.core.event;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.framework.log.Logger;


public class SchedulerEventHandler {

	private Logger log = Logger.getLogger(SchedulerEventHandler.class);

	private ExecutorService ExecutorService = Executors.newCachedThreadPool();

	private CopyOnWriteArrayList<SchedulerEventListener> schedulerEventListener = new CopyOnWriteArrayList<SchedulerEventListener>();

	private boolean async;

	private final Scheduler scheduler;

	public SchedulerEventHandler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean add(SchedulerEventListener listener) {
		return this.schedulerEventListener.add(listener);
	}

	public void fireAddTaskScheduleEntry(
			final TaskScheduleEntry taskScheduleEntry) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.addTaskScheduleEntry(scheduler, taskScheduleEntry);
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

	public void fireExceptionTask(final Object task, final Exception e) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.exceptionTask(scheduler, task, e);
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

	public void fireRegistTaskAfterScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.resigtTaskAfterScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireRegistTaskBeforeScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.resigtTaskBeforeScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	public void fireRemoveTaskScheduleEntry(
			final TaskScheduleEntry taskScheduleEntry) {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.removeTaskScheduleEntry(scheduler,
							taskScheduleEntry);
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

	public boolean isAsync() {
		return async;
	}

	public boolean remove(SchedulerEventListener listener) {
		return this.schedulerEventListener.remove(listener);
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	private void waitFuture(Future<?> future) {
		if (!async) {
			try {
				future.get();
			} catch (InterruptedException e) {
			} catch (ExecutionException e) {
				log.error("実行例外が発生しました", e);
			}
		}
	}
}

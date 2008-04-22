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

	/**
	 * コンストラクタ
	 * 
	 * @param scheduler
	 *            スケジューラ
	 */
	public SchedulerEventHandler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * リスナーを追加する．
	 * 
	 * @param listener
	 *            リスナー
	 * @return この呼び出しの結果，このコレクションが変更された場合はtrue，それ以外はfalse
	 */
	public boolean add(SchedulerEventListener listener) {
		return this.schedulerEventListener.add(listener);
	}

	/**
	 * 
	 * @param taskScheduleEntry
	 */
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

	/**
	 * タスクのキャンセルイベントを発火します．
	 * 
	 * @param task
	 *            タスク
	 */
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

	/**
	 * スケジューラ終了イベントを発火します．
	 */
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

	/**
	 * タスクの例外イベントを発火します．
	 * 
	 * @param task
	 *            タスク
	 * @param e
	 *            例外
	 */
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

	/**
	 * タスクの終了イベントを発火します．
	 * 
	 * @param task
	 *            タスク
	 */
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

	/**
	 * スケジューラの一時停止イベントを発火します．
	 */
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

	/**
	 * スケジューラの再開イベントを発火します．
	 */
	public void fireResumeScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.resumeScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	/**
	 * タスク登録後イベントを発火します．
	 */
	public void fireRegisterTaskAfterScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.resigtTaskAfterScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	/**
	 * タスク登録前イベントを発火します．
	 */
	public void fireRegisterTaskBeforeScheduler() {
		for (final SchedulerEventListener listener : schedulerEventListener) {
			Future<?> future = ExecutorService.submit(new Runnable() {
				public void run() {
					listener.resigtTaskBeforeScheduler(scheduler);
				}
			});
			waitFuture(future);
		}
	}

	/**
	 * 
	 * @param taskScheduleEntry
	 */
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

	/**
	 * スケジューラのシャットダウンイベントを発火します．
	 */
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

	/**
	 * スケジューラの開始イベントを発火します．
	 */
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

	/**
	 * タスクの開始イベントを発火します．
	 * 
	 * @param task
	 *            タスク
	 */
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

	/**
	 * 非同期フラグを返します．
	 * 
	 * @return 非同期フラグ．非同期はtrue, 同期はfalse
	 */
	public boolean isAsync() {
		return async;
	}

	/**
	 * リスナーを削除します．
	 * 
	 * @param listener
	 *            リスナー
	 * @return リストが指定された要素を保持している場合はtrue，以外はfalse
	 * 
	 */
	public boolean remove(SchedulerEventListener listener) {
		return this.schedulerEventListener.remove(listener);
	}

	/**
	 * 非同期フラグを設定します．
	 * 
	 * @param async
	 *            非同期フラグ．非同期はtrue, 同期はfalse
	 */
	public void setAsync(boolean async) {
		this.async = async;
	}

	/**
	 * Futureオブジェクトを待機します．
	 * 
	 * @param future
	 *            Future
	 */
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

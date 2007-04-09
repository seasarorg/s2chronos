package org.seasar.chronos.core.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.core.SchedulerConfiguration;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.event.SchedulerEventHandler;
import org.seasar.chronos.core.exception.ExecutionRuntimeException;
import org.seasar.chronos.core.exception.InterruptedRuntimeException;
import org.seasar.chronos.core.handler.ScheduleExecuteHandler;
import org.seasar.chronos.core.util.TaskPropertyUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class SchedulerImpl extends AbstractScheduler {

	public static final int SHUTDOWN_AWAIT_TIME = 10;

	public static final TimeUnit SHUTDOWN_AWAIT_TIMEUNIT = TimeUnit.MILLISECONDS;

	private SchedulerEventHandler schedulerEventHandler = new SchedulerEventHandler(
			this);

	private AtomicBoolean pause = new AtomicBoolean();

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Future<Void> schedulerTaskFuture;

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	private SchedulerConfiguration configuration = defaultConfiguration;

	private static final SchedulerConfiguration defaultConfiguration = new SchedulerConfiguration();

	private ScheduleExecuteHandler scheduleExecuteWaitHandler;

	private ScheduleExecuteHandler scheduleExecuteStartHandler;

	private ScheduleExecuteHandler scheduleExecuteShutdownHandler;

	public void setScheduleExecuteShutdownHandler(
			ScheduleExecuteHandler sheduleExecuteShutdownHandler) {
		this.scheduleExecuteShutdownHandler = sheduleExecuteShutdownHandler;
	}

	public void setScheduleExecuteStartHandler(
			ScheduleExecuteHandler sheduleExecuteStartHandler) {
		this.scheduleExecuteStartHandler = sheduleExecuteStartHandler;
	}

	public void setScheduleExecuteWaitHandler(
			ScheduleExecuteHandler sheduleExecuteWaitHandler) {
		this.scheduleExecuteWaitHandler = sheduleExecuteWaitHandler;
	}

	public boolean addListener(SchedulerEventListener listener) {
		return schedulerEventHandler.add(listener);
	}

	public boolean removeListener(SchedulerEventListener listener) {
		return schedulerEventHandler.remove(listener);
	}

	public SchedulerConfiguration getSchedulerConfiguration() {
		return configuration;
	}

	public void join() {
		try {
			this.schedulerTaskFuture.get();
		} catch (CancellationException e) {
			log.debug(e);
			while (!schedulerTaskFuture.isDone()) {
				log.log("DCHRONOS0013", null);
			}
			log.debug("キャンセルチェック完了");
			this.schedulerEventHandler.fireShutdownScheduler();
		} catch (ExecutionException e) {
			log.log("ECHRONOS0002", null, e);
			throw new ExecutionRuntimeException(e);
		} catch (InterruptedException e) {
			throw new InterruptedRuntimeException(e);
		} finally {
			if (!this.schedulerTaskFuture.isCancelled()) {
				this.schedulerEventHandler.fireEndScheduler();
			}
		}
	}

	public synchronized void pause() {
		this.pause.set(!this.pause.get());
		this.notify();
	}

	public boolean isPaused() {
		return pause.get();
	}

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration) {
		this.configuration = schedulerConfiguration;
	}

	public void shutdown() {
		this.taskContenaStateManager.forEach(TaskStateType.RUNNING,
				new TaskContenaStateManager.TaskContenaHanlder() {
					public Object processTaskContena(TaskContena taskContena) {
						taskContena.getTaskExecutorService().cancel();
						try {
							while (!taskContena.getTaskExecutorService().await(
									SHUTDOWN_AWAIT_TIME,
									SHUTDOWN_AWAIT_TIMEUNIT)) {
								String taskName = TaskPropertyUtil
										.getTaskName(taskContena
												.getTaskExecutorService());
								log.debug("Task (" + taskName
										+ ") のShutdown 待機中");
							}
						} catch (InterruptedException e) {
							throw new InterruptedRuntimeException(e);
						}
						return null;
					}
				});
		schedulerTaskFuture.cancel(true);
	}

	private long finishStartTime = 0;

	private boolean getSchedulerFinish() {
		// log.debug("getSchedulerFinish start");
		if (finishStartTime != 0
				&& taskContenaStateManager.size(TaskStateType.SCHEDULED) > 0) {
			finishStartTime = 0;
			log.debug(">>>>>>>>>>>>>>>>>getSchedulerFinish finish cancel!!!");
			return false;
		}
		if (finishStartTime != 0
				&& (System.currentTimeMillis() - finishStartTime) >= configuration
						.getZeroScheduleTime()) {
			log
					.debug(">>>>>>>>>>>>>>>>>getSchedulerFinish finish!! return true");
			finishStartTime = 0;
			return true;
		}
		if (this.schedulerTaskFuture != null
				&& taskContenaStateManager.size(TaskStateType.SCHEDULED) == 0
				&& taskContenaStateManager.size(TaskStateType.RUNNING) == 0
				&& configuration.isAutoFinish() && finishStartTime == 0) {
			finishStartTime = System.currentTimeMillis();
			log.debug(">>>>>>>>>>>>>>>>>getSchedulerFinish finish start");
		}
		// log.debug("getSchedulerFinish return false");
		return false;
	}

	public void start() {

		this.schedulerEventHandler.fireRegistTaskBeforeScheduler();

		this.registTaskFromS2Container();
		final ScheduleExecuteHandler[] scheduleExecuteHandlers = this
				.setupHandler();

		this.schedulerEventHandler.fireRegistTaskAfterScheduler();

		this.schedulerTaskFuture = this.executorService
				.submit(new Callable<Void>() {
					public Void call() throws Exception {
						do {
							for (ScheduleExecuteHandler seh : scheduleExecuteHandlers) {
								seh.handleRequest();
							}
						} while (!getSchedulerFinish());
						return null;
					}
				});
		this.schedulerEventHandler.fireStartScheduler();
	}

	private ScheduleExecuteHandler[] setupHandler() {
		ScheduleExecuteHandler[] scheduleExecuteHandlers = new ScheduleExecuteHandler[] {
				scheduleExecuteWaitHandler, scheduleExecuteStartHandler,
				scheduleExecuteShutdownHandler };

		this.scheduleExecuteWaitHandler
				.setExecutorService(this.executorService);
		this.scheduleExecuteWaitHandler.setPause(this.pause);

		this.scheduleExecuteStartHandler
				.setExecutorService(this.executorService);
		this.scheduleExecuteStartHandler
				.setSchedulerEventHandler(this.schedulerEventHandler);

		this.scheduleExecuteShutdownHandler
				.setExecutorService(this.executorService);
		this.scheduleExecuteShutdownHandler
				.setSchedulerEventHandler(this.schedulerEventHandler);

		return scheduleExecuteHandlers;
	}

	public boolean addTask(String taskName) {
		ComponentDef componentDef = this.s2container.getComponentDef(taskName);
		if (componentDef == null) {
			componentDef = findTaskComponentDefByTaskName(taskName);
		}
		if (componentDef != null) {
			scheduleTask(componentDef);
			return true;
		}
		return false;
	}

	public void addTask(Class componentClass) {
		scheduleTask(this.s2container, componentClass);
	}

	protected TaskContena scheduleTask(ComponentDef componentDef) {
		TaskContena tc = super.scheduleTask(componentDef);
		this.taskContenaStateManager
				.addTaskContena(TaskStateType.SCHEDULED, tc);
		this.schedulerEventHandler.fireAddTask(tc.getTaskExecutorService()
				.getTask());
		return tc;
	}

	/**
	 * S2コンテナ上のコンポーネントを検索し，スケジューラに登録します．
	 * 
	 */
	protected void registTaskFromS2Container() {
		final S2Container target = this.s2container.getRoot();
		this.registChildTaskComponent(target);
		this.registTaskFromS2ContainerOnSmartDeploy(target);
	}

	public boolean removeTask(Object task) {
		TaskContena taskContena = this.taskContenaStateManager
				.getTaskContena(task);
		this.taskContenaStateManager.removeTaskContena(
				TaskStateType.UNSCHEDULED, taskContena);
		return false;
	}

}

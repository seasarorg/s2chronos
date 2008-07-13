/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.core.SchedulerConfiguration;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.event.SchedulerEventHandler;
import org.seasar.chronos.core.exception.CancellationRuntimeException;
import org.seasar.chronos.core.exception.ExecutionRuntimeException;
import org.seasar.chronos.core.exception.InterruptedRuntimeException;
import org.seasar.chronos.core.executor.ExecutorServiceFactory;
import org.seasar.chronos.core.handler.ScheduleExecuteHandler;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;

public class SchedulerImpl extends AbstractScheduler {

	public static final int SHUTDOWN_AWAIT_TIME = 10;

	public static final TimeUnit SHUTDOWN_AWAIT_TIMEUNIT = TimeUnit.MILLISECONDS;

	private static final SchedulerConfiguration defaultConfiguration = new SchedulerConfiguration();

	private final SchedulerEventHandler schedulerEventHandler = new SchedulerEventHandler(
			);

	private final AtomicBoolean pause = new AtomicBoolean();

	private ExecutorService executorService;

	private Future<Void> schedulerTaskFuture;

	private final TaskScheduleEntryManager taskScheduleEntryManager = TaskScheduleEntryManager
			.getInstance();

	private SchedulerConfiguration configuration = defaultConfiguration;

	private final List<ScheduleExecuteHandler> scheduleExecuteHandlerList = new ArrayList<ScheduleExecuteHandler>();

	private ExecutorServiceFactory executorServiceFactory;

	private long finishStartTime = 0;

	private boolean initialized;

	public SchedulerImpl() {
		schedulerEventHandler.setScheduler(this);
	}

	public void addScheduleExecuteHandler(
			ScheduleExecuteHandler scheduleExecuteHandler) {
		this.scheduleExecuteHandlerList.add(scheduleExecuteHandler);
	}

	private synchronized void initialize() {
		if (this.initialized) {
			return;
		}
		executorService = executorServiceFactory.create(this.configuration
				.getThreadPoolType(), this.configuration.getThreadPoolSize(),
				this.configuration.isDaemon());
		schedulerEventHandler.setExecutorServiceFacotry(executorServiceFactory);
		this.initialized = true;
	}

	/**
	 * リスナーを追加します．
	 * 
	 * @param listener
	 *            リスナー
	 */
	public boolean addListener(SchedulerEventListener listener) {
		return this.schedulerEventHandler.add(listener);
	}

	/**
	 * タスクを追加します．
	 * 
	 * @param taskComponentClass
	 *            タスクコンポーネント
	 */
	public synchronized boolean addTask(Class<?> taskComponentClass) {
		S2Container rootS2Container = this.s2container.getRoot();
		boolean result = false;
		result = this.registerChildTaskComponentByTarget(rootS2Container,
				taskComponentClass);
		result = result
				|| this.registerTaskFromS2ContainerOnSmartDeployByTarget(
						rootS2Container, taskComponentClass);
		return result;
	}

	/**
	 * {@link SchedulerConfiguration}を返します．
	 * 
	 * @return {@link SchedulerConfiguration}
	 */
	public SchedulerConfiguration getSchedulerConfiguration() {
		return this.configuration;
	}

	/**
	 * スケジューラの終了条件を返します．
	 * 
	 * @return trueなら終了，falseなら終了しない
	 */
	private boolean getSchedulerFinish() {
		if (this.finishStartTime != 0
				&& this.taskScheduleEntryManager.size(TaskStateType.SCHEDULED) > 0) {
			this.finishStartTime = 0;
			return false;
		}
		if (this.finishStartTime != 0
				&& System.currentTimeMillis() - this.finishStartTime >= this.configuration
						.getAutoFinishTimeLimit()) {
			this.finishStartTime = 0;
			return true;
		}
		if (this.schedulerTaskFuture != null
				&& this.taskScheduleEntryManager.size(TaskStateType.SCHEDULED) == 0
				&& this.taskScheduleEntryManager.size(TaskStateType.RUNNING) == 0
				&& this.configuration.isAutoFinish()
				&& this.finishStartTime == 0) {
			this.finishStartTime = System.currentTimeMillis();
		}
		return false;
	}

	/**
	 * 一時停止状態かどうかを返します．
	 * 
	 * @return 一時停止中ならtrue,それ以外ならfalse
	 */
	public boolean isPaused() {
		return this.pause.get();
	}

	/**
	 * スケジューラの終了を待機します．
	 */
	public void join() {
		if (this.configuration.isDaemon()) {
			return;
		}
		log.log("DCHRONOS0016", null);
		try {
			this.schedulerTaskFuture.get();
		} catch (CancellationException e) {
			throw new CancellationRuntimeException(e);
		} catch (ExecutionException e) {
			if (e.getCause() instanceof InterruptedException) {
				throw new InterruptedRuntimeException(e.getCause());
			}
			throw new ExecutionRuntimeException(e);
		} catch (InterruptedException e) {
			throw new InterruptedRuntimeException(e);
		} finally {
			if (!this.schedulerTaskFuture.isCancelled()) {
				this.schedulerEventHandler.fireEndScheduler();
			}
		}
		log.log("DCHRONOS0017", null);
	}

	/**
	 * スケジューラを一時停止/再開させます．
	 * 
	 */
	public synchronized void pause() {
		if (!this.pause.get()) {
			log.log("DCHRONOS0018", null);
		} else {
			log.log("DCHRONOS0021", null);
		}
		this.pause.set(!this.pause.get());
		this.notify();
	}

	/**
	 * S2コンテナ上のコンポーネントを検索し，スケジューラに登録します．
	 */
	@Override
	protected void registerTaskFromS2Container() {
		final S2Container target = this.s2container.getRoot();
		this.registerTaskFromS2ContainerOnSmartDeploy(target);
		this.registerChildTaskComponent(target);
		this.registerJavascriptTaskComponent();
	}

	/**
	 * リスナーを削除します．
	 * 
	 * @param listener
	 *            リスナー
	 */
	public boolean removeListener(SchedulerEventListener listener) {
		return this.schedulerEventHandler.remove(listener);
	}

	/**
	 * タスクを削除します．
	 * 
	 * @param taskClass
	 *            タスククラス
	 */
	public synchronized boolean removeTask(Class<?> taskClass) {
		TaskScheduleEntry taskScheduleEntry = this.taskScheduleEntryManager
				.getTaskScheduleEntry(taskClass);
		return this.taskScheduleEntryManager
				.removeTaskScheduleEntry(taskScheduleEntry);
	}

	protected TaskScheduleEntry unscheduleTask(final ComponentDef componentDef) {
		TaskScheduleEntry target = (TaskScheduleEntry) this.taskScheduleEntryManager
				.forEach(new TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry scheduleEntry) {
						if (componentDef
								.equals(scheduleEntry.getComponentDef())) {
							return scheduleEntry;
						}
						return null;
					}
				});
		if (this.taskScheduleEntryManager.removeTaskScheduleEntry(target)) {
			return target;
		}
		return null;
	}

	@Override
	protected TaskScheduleEntry scheduleTask(ComponentDef taskComponentDef) {
		return this.scheduleTask(taskComponentDef, false);
	}

	@Override
	protected TaskScheduleEntry scheduleTask(ComponentDef taskComponentDef,
			boolean force) {
		boolean contains = this.taskScheduleEntryManager
				.contains(taskComponentDef.getComponentClass());
		if (contains) {
			return null;
		}
		TaskScheduleEntry taskScheduleEntry = super.scheduleTask(
				taskComponentDef, force);
		if (taskScheduleEntry == null) {
			return null;
		}
		this.taskScheduleEntryManager.addTaskScheduleEntry(
				TaskStateType.SCHEDULED, taskScheduleEntry);
		this.schedulerEventHandler.fireAddTaskScheduleEntry(TaskStateType.SCHEDULED,taskScheduleEntry);
		return taskScheduleEntry;
	}

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration) {
		this.configuration = schedulerConfiguration;
	}

	/**
	 * スケジューラ実行ハンドラーをセットアップします．
	 * 
	 * @return スケジューラ実行ハンドラーの配列
	 */
	private void setupHandler() {
		for (ScheduleExecuteHandler seh : this.scheduleExecuteHandlerList) {
			seh.setExecutorService(executorService);
			seh.setPause(pause);
			seh.setSchedulerEventHandler(schedulerEventHandler);
		}
	}

	public void shutdown() {
		log.log("DCHRONOS0013", null);
		this.taskScheduleEntryManager.forEach(TaskStateType.RUNNING,
				new TaskScheduleEntryManager.TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry taskScheduleEntry) {
						taskScheduleEntry.getTaskExecutorService().cancel();
						try {
							while (!taskScheduleEntry.getTaskExecutorService()
									.await(SHUTDOWN_AWAIT_TIME,
											SHUTDOWN_AWAIT_TIMEUNIT)) {
								String taskName = taskScheduleEntry
										.getTaskExecutorService()
										.getTaskPropertyReader().getTaskName(
												null);
								log.log("DCHRONOS0014",
										new Object[] { taskName });
							}
						} catch (InterruptedException e) {
							throw new InterruptedRuntimeException(e);
						}
						return null;
					}
				});
		this.schedulerTaskFuture.cancel(true);
		this.schedulerEventHandler.fireShutdownScheduler();
		log.log("DCHRONOS0015", null);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.chronos.core.Scheduler#start()
	 */
	public void start() {
		this.initialize();
		log.log("DCHRONOS0011", null);
		this.schedulerEventHandler.fireRegisterTaskBeforeScheduler();
		this.registerTaskFromS2Container();
		this.setupHandler();
		this.schedulerEventHandler.fireRegisterTaskAfterScheduler();

		this.schedulerTaskFuture = this.executorService
				.submit(new Callable<Void>() {
					public Void call() throws InterruptedException {
						do {
							for (ScheduleExecuteHandler seh : scheduleExecuteHandlerList) {
								seh.handleRequest();
							}
							Thread.sleep(SchedulerImpl.this.configuration
									.getTaskScanIntervalTime());
						} while (!SchedulerImpl.this.getSchedulerFinish());
						return null;
					}
				});
		this.schedulerEventHandler.fireStartScheduler();
		log.log("DCHRONOS0012", null);
	}

	public void setExecutorServiceFactory(
			ExecutorServiceFactory executorServiceFactory) {
		this.executorServiceFactory = executorServiceFactory;
	}

}

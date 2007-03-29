package org.seasar.chronos.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerConfiguration;
import org.seasar.chronos.SchedulerEventListener;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.autodetector.TaskClassAutoDetector;
import org.seasar.chronos.exception.ExecutionRuntimeException;
import org.seasar.chronos.handler.ScheduleExecuteHandler;
import org.seasar.chronos.logger.Logger;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.util.TaskPropertyUtil;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.SmartDeployUtil;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.util.ClassTraversal;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class SchedulerImpl implements Scheduler {

	public static final int SHUTDOWN_AWAIT_TIME = 10;

	public static final TimeUnit SHUTDOWN_AWAIT_TIMEUNIT = TimeUnit.MILLISECONDS;

	private static Logger log = Logger.getLogger(SchedulerImpl.class);

	private AtomicBoolean pause = new AtomicBoolean();

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Future<Void> schedulerTaskFuture;

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	private S2Container s2container;

	private SchedulerConfiguration configuration = defaultConfiguration;

	private static final SchedulerConfiguration defaultConfiguration = new SchedulerConfiguration();

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	private TaskClassAutoDetector taskClassAutoDetector;

	public void setTaskClassAutoDetector(
			TaskClassAutoDetector taskClassAutoDetector) {
		this.taskClassAutoDetector = taskClassAutoDetector;
	}

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

	public void addListener(SchedulerEventListener listener) {

	}

	public void removeListener(SchedulerEventListener listener) {

	}

	public SchedulerConfiguration getSchedulerConfiguration() {
		return configuration;
	}

	public void join() throws InterruptedException {
		try {
			this.schedulerTaskFuture.get();
		} catch (CancellationException e) {
			log.debug(e);
			while (!schedulerTaskFuture.isDone()) {
				log.log("DCHRONOS0013", null);
			}
			log.debug("キャンセルチェック完了");
		} catch (ExecutionException e) {
			log.log("ECHRONOS0002", null, e);
			throw new ExecutionRuntimeException(e);
		}
	}

	public void pause() {
		this.pause.set(!this.pause.get());
		this.notify();
	}

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration) {
		this.configuration = schedulerConfiguration;
	}

	public void shutdown() throws InterruptedException {
		// キャンセルしたタスクが残っていれば
		final List<TaskContena> runningTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);
		for (TaskContena tc : runningTaskList) {
			tc.getTaskExecutorService().cancel();
			while (!tc.getTaskExecutorService().await(SHUTDOWN_AWAIT_TIME,
					SHUTDOWN_AWAIT_TIMEUNIT)) {
				String taskName = TaskPropertyUtil.getTaskName(tc
						.getTaskExecutorService());
				log.debug("Task (" + taskName + ") のShutdown 待機中");
			}
		}
		schedulerTaskFuture.cancel(true);
	}

	private boolean getSchedulerFinish() {
		final List<TaskContena> runingTaskList = taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);
		if (this.schedulerTaskFuture != null && runingTaskList.size() == 0
				&& configuration.isAutoFinish()) {
			return true;
		}
		return false;
	}

	public void start() {
		this.registTaskFromS2Container();
		this.setupHandler();
		this.schedulerTaskFuture = this.executorService
				.submit(new Callable<Void>() {
					public Void call() throws Exception {
						do {
							scheduleExecuteWaitHandler.handleRequest();
							scheduleExecuteStartHandler.handleRequest();
							scheduleExecuteShutdownHandler.handleRequest();
						} while (!getSchedulerFinish());
						return null;
					}
				});
	}

	private void setupHandler() {
		this.scheduleExecuteWaitHandler
				.setExecutorService(this.executorService);
		this.scheduleExecuteWaitHandler.setPause(this.pause);
		this.scheduleExecuteStartHandler
				.setExecutorService(this.executorService);
		this.scheduleExecuteShutdownHandler
				.setExecutorService(this.executorService);
	}

	private void registChildTaskComponent(S2Container targetContainer) {
		Traversal.forEachComponent(targetContainer,
				new Traversal.ComponentDefHandler() {
					public Object processComponent(ComponentDef componentDef) {
						Class<?> clazz = componentDef.getComponentClass();
						Task task = (Task) clazz.getAnnotation(Task.class);
						if (task != null) {
							scheduleTask(componentDef);
						}
						return null;
					}

				});
	}

	private void scheduleTask(ComponentDef componentDef) {
		CopyOnWriteArrayList<TaskContena> list = taskContenaStateManager
				.getTaskContenaList(TaskStateType.SCHEDULED);
		TaskContena tc = new TaskContena(componentDef);
		final TaskExecutorService tes = (TaskExecutorService) this.s2container
				.getComponent(TaskExecutorService.class);
		tc.setTaskExecutorService(tes);
		tes.setTaskComponentDef(tc.getComponentDef());
		tes.setGetterSignal(this);
		// ここでタスクに対してDIが実行されます
		// なので，ここ以外のところで，getComponentしないように注意!
		tes.prepare();
		tc.setTask(tes.getTask());
		list.addIfAbsent(tc);
	}

	private void registTaskFromS2Container() {
		final S2Container target = this.s2container.getRoot();
		this.registChildTaskComponent(target);
		this.registTaskFromS2ContainerOnSmartDeploy(target);
	}

	private void registTaskFromS2ContainerOnSmartDeploy(
			final S2Container s2Container) {
		if (SmartDeployUtil.isSmartdeployMode(s2Container)) {
			this.taskClassAutoDetector
					.detect(new ClassTraversal.ClassHandler() {
						public void processClass(String packageName,
								String shortClassName) {
							String name = ClassUtil.concatName(packageName,
									shortClassName);
							Class clazz = ReflectionUtil
									.forNameNoException(name);
							ComponentDef componentDef = s2Container
									.getComponentDef(clazz);
							scheduleTask(componentDef);
						}
					});
		}
	}

}

package org.seasar.chronos.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerConfig;
import org.seasar.chronos.SchedulerEventListener;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.exception.SchedulerException;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.chronos.trigger.Trigger;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.Traversal;
import org.seasar.framework.log.Logger;

public class SchedulerImpl implements Scheduler {

	private static Logger log = Logger.getLogger(SchedulerImpl.class);

	private static final String TASK_TYPE_SCHEDULED = "SCHEDULED_TASK";

	private static final String TASK_TYPE_RUNTASK = "RUN_TASK";

	private static final String TASK_TYPE_CANCELTASK = "CANCEL_TASK";

	private static final int SCAN_INTERVAL_TIME = 1000;

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Future<Void> schedulerTaskFuture;

	private ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>>();

	private S2Container s2container;

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	private CopyOnWriteArrayList<TaskContena> getTaskContenaMap(String key) {
		CopyOnWriteArrayList<TaskContena> result = this.taskContenaMap.get(key);
		if (result == null) {
			result = new CopyOnWriteArrayList<TaskContena>();
			this.putMasterTaskContena(key, result);
		}
		return result;
	}

	private void putMasterTaskContena(String key,
			CopyOnWriteArrayList<TaskContena> taskContenaList) {
		this.taskContenaMap.put(key, taskContenaList);
	}

	public void addListener(SchedulerEventListener listener) {

	}

	public SchedulerConfig getConfig() {
		return null;
	}

	public void join() throws InterruptedException {
		try {
			this.schedulerTaskFuture.get();
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

	public void shutdown() throws InterruptedException {

	}

	public void shutdown(boolean waitAllJobFinish) throws InterruptedException {
		final CopyOnWriteArrayList<TaskContena> cancelTaskList = getTaskContenaMap(TASK_TYPE_CANCELTASK);
		taskFinisher(true);
		for (TaskContena tc : cancelTaskList) {
			while (!tc.getFuture().isDone()) {
				;
			}
		}
		// this.executorService.shutdown();
		// this.executorService.awaitTermination(3600, TimeUnit.SECONDS);
	}

	public void start() throws SchedulerException {
		// コンテナからタスクを取りだす
		this.getTaskFromS2Container();
		this.schedulerTaskFuture = executorService.submit(new Callable<Void>() {
			public Void call() throws Exception {
				while (true) {
					TimeUnit.MILLISECONDS.sleep(SCAN_INTERVAL_TIME);
					taskStarter();
					taskFinisher();
				}
			}
		});
	}

	private boolean getShutdownTask(TaskProperties prop) {
		boolean shutdown = prop.getShutdownTask();
		return shutdown;
	}

	private void setShutdownTask(TaskProperties prop, boolean shutdownTask) {
		prop.setShutdownTask(shutdownTask);
	}

	private boolean getEndTask(TaskProperties prop) {
		boolean end = false;
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			end = prop.getEndTask();
		} else {
			end = trigger.getEndTask();
		}
		return end;
	}

	private void setEndTask(TaskProperties prop, boolean endTask) {
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			prop.setEndTask(endTask);
		} else {
			trigger.setEndTask(endTask);
		}
	}

	private void taskFinisher() throws InterruptedException {
		taskFinisher(false);
	}

	private void taskFinisher(boolean force) throws InterruptedException {
		final CopyOnWriteArrayList<TaskContena> runTaskList = getTaskContenaMap(TASK_TYPE_RUNTASK);
		final CopyOnWriteArrayList<TaskContena> cancelTaskList = getTaskContenaMap(TASK_TYPE_CANCELTASK);
		for (final TaskContena tc : runTaskList) {
			final TaskExecutorService tes = (TaskExecutorService) this.s2container
					.getComponent(TaskExecutorService.class);
			tes.setTaskComponentDef(tc.getComponentDef());
			tes.prepare();
			boolean endOrShutdownFlag = false;
			if (this.getEndTask(tes)) {
				endOrShutdownFlag = true;
				this.setEndTask(tes, false);
			}
			if (this.getShutdownTask(tes)) {
				endOrShutdownFlag = true;
				this.setShutdownTask(tes, false);
			}
			if (endOrShutdownFlag || force) {
				final Future<TaskExecutorService> future = this.executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								synchronized (runTaskList) {
									runTaskList.notify();
								}
								tes.cancel();
								if (tes.await(30, TimeUnit.SECONDS) == false) {
									// TODO キャンセルできなかった．例外をスローすること．
								}
								if (cancelTaskList.contains(tc)) {
									cancelTaskList.remove(tc);
								}
								return tes;
							}
						});
				synchronized (runTaskList) {
					tc.setFuture(future);
					tc.setTaskExecutorService(tes);
					cancelTaskList.add(tc);
					runTaskList.remove(tc);
					runTaskList.wait();
				}
			}
		}
	}

	private boolean getStartTask(TaskProperties prop) {
		boolean start = false;
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			start = prop.getStartTask();
			prop.setStartTask(false);
		} else {
			start = trigger.getStartTask();
		}
		return start;
	}

	private void setStartTask(TaskProperties prop, boolean startTask) {
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			prop.setStartTask(startTask);
		} else {
			trigger.setStartTask(startTask);
		}
	}

	private void taskStarter() throws InterruptedException {
		final CopyOnWriteArrayList<TaskContena> taskList = getTaskContenaMap(TASK_TYPE_SCHEDULED);
		final CopyOnWriteArrayList<TaskContena> runTaskList = getTaskContenaMap(TASK_TYPE_RUNTASK);
		for (final TaskContena tc : taskList) {
			final TaskExecutorService tes = (TaskExecutorService) s2container
					.getComponent(TaskExecutorService.class);
			tes.setTaskComponentDef(tc.getComponentDef());
			tes.prepare();
			if (this.getStartTask(tes)) {
				this.setStartTask(tes, false);
				// タスクの開始
				Future<TaskExecutorService> future = executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								synchronized (runTaskList) {
									runTaskList.notify();
								}
								String nextTaskName = tes.initialize();
								if (nextTaskName != null) {
									tes.execute(nextTaskName);
									tes.waitOne();
								}
								tes.destroy();
								if (runTaskList.contains(tc)) {
									runTaskList.remove(tc);
								}
								return tes;
							}
						});
				synchronized (runTaskList) {
					tc.setFuture(future);
					tc.setTaskExecutorService(tes);
					runTaskList.add(tc);
					taskList.remove(tc);
					runTaskList.wait();
				}
			}
		}
	}

	private void findChildComponent(S2Container targetContainer) {
		Traversal.forEachComponent(targetContainer,
				new Traversal.ComponentDefHandler() {
					public Object processComponent(ComponentDef componentDef) {
						Class clazz = componentDef.getComponentClass();
						Task task = (Task) clazz.getAnnotation(Task.class);
						if (task != null) {
							CopyOnWriteArrayList<TaskContena> list = getTaskContenaMap(TASK_TYPE_SCHEDULED);
							list.addIfAbsent(new TaskContena(componentDef));
						}
						return null;
					}
				});
	}

	private void getTaskFromS2Container() {
		S2Container target = this.s2container.getRoot();
		findChildComponent(target);
	}

	public boolean addTask(Object task) {
		final CopyOnWriteArrayList<TaskContena> taskList = getTaskContenaMap(TASK_TYPE_SCHEDULED);
		TaskContena tc = new TaskContena();
		tc.setTarget(task);
		tc.setTargetClass(task.getClass());
		return taskList.add(tc);
	}

	public boolean removeTask(Object task) {
		final CopyOnWriteArrayList<TaskContena> taskList = getTaskContenaMap(TASK_TYPE_SCHEDULED);
		for (TaskContena tc : taskList) {
			if (tc.getTarget() == task) {
				return taskList.remove(tc);
			}
		}
		return false;
	}

}

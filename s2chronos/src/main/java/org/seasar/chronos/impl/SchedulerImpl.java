package org.seasar.chronos.impl;

import java.util.List;
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

	private static final String TASK_TYPE_SCHEDULED = "SCHEDULED";

	private static final String TASK_TYPE_RUNTASK = "RUNTASK";

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private Future<Void> future;

	private ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>>();

	private List<Future<TaskExecutorService>> futureList = new CopyOnWriteArrayList<Future<TaskExecutorService>>();

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
		this.getTaskFromContainer();

		future = executorService.submit(new Callable<Void>() {

			public Void call() throws Exception {
				while (true) {
					TimeUnit.SECONDS.sleep(1);
					taskStarter();
					taskEnder();
				}
			}

		});
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

	private void taskEnder() throws InterruptedException {
		CopyOnWriteArrayList<TaskContena> runTaskList = getTaskContenaMap(TASK_TYPE_RUNTASK);
		for (TaskContena tc : runTaskList) {
			final TaskExecutorService tes = (TaskExecutorService) s2container
					.getComponent(TaskExecutorService.class);
			tes.setTaskComponentDef(tc.getComponentDef());
			tes.prepare();
			if (getEndTask(tes)) {
				tes.cancel();
				while (!tes.await(1, TimeUnit.SECONDS)) {
					;
				}
			}
		}
	}

	private boolean getStartTask(TaskProperties prop) {
		boolean start = false;
		Trigger trigger = prop.getTrigger();
		if (trigger == null) {
			start = prop.getStartTask();
		} else {
			start = trigger.getStartTask();
		}
		return start;
	}

	private void taskStarter() throws InterruptedException {
		final CopyOnWriteArrayList<TaskContena> taskList = getTaskContenaMap(TASK_TYPE_SCHEDULED);
		final CopyOnWriteArrayList<TaskContena> runTaskList = getTaskContenaMap(TASK_TYPE_RUNTASK);
		for (final TaskContena tc : taskList) {
			final TaskExecutorService tes = (TaskExecutorService) s2container
					.getComponent(TaskExecutorService.class);
			tes.setTaskComponentDef(tc.getComponentDef());
			tes.prepare();
			if (getStartTask(tes)) {
				// タスクの開始
				Future<TaskExecutorService> future = executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								String nextTaskName = tes.initialize();
								if (nextTaskName != null) {
									tes.execute(nextTaskName);
								}
								// log.debug("waitOne s");
								tes.waitOne();
								// log.debug("waitOne e");
								tes.destroy();
								runTaskList.remove(tc);
								return tes;
							}
						});

				futureList.add(future);
				tc.setFuture(future);
				tc.setTaskExecutorService(tes);
				runTaskList.add(tc);
				taskList.remove(tc);
			}
		}
	}

	private void findComponent(S2Container targetContainer) {
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

	private void findChildComponent(S2Container targetContainer) {
		findComponent(targetContainer);
		// for (int i = 0; i < targetContainer.getChildSize(); i++) {
		// targetContainer = targetContainer.getChild(i);
		// findChildComponent(targetContainer);
		// }
	}

	private void getTaskFromContainer() {
		S2Container target = this.s2container.getRoot();
		findChildComponent(target);
	}

	public boolean addTask(Object task) {

		return false;
	}

	public boolean removeTask(Object task) {

		return false;
	}

}

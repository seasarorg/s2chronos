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
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.util.Traversal;

public class SchedulerImpl implements Scheduler {

	private static final String TASK_TYPE_SCHEDULED = "SCHEDULED";

	private ExecutorService executorService = Executors
			.newSingleThreadExecutor();

	private Future<Void> future;

	private ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<String, CopyOnWriteArrayList<TaskContena>>();

	private S2Container s2container;

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	private CopyOnWriteArrayList<TaskContena> getTaskContenaMap(String key) {
		CopyOnWriteArrayList<TaskContena> result = this.taskContenaMap.get(key);
		if (result == null) {
			result = new CopyOnWriteArrayList<TaskContena>();
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
				CopyOnWriteArrayList<TaskContena> taskList = getTaskContenaMap(TASK_TYPE_SCHEDULED);
				for (TaskContena tc : taskList) {
					TaskExecutorService tes = (TaskExecutorService) s2container
							.getComponent(TaskExecutorService.class);
					tes.initialize(tc.getComponentDef());

				}
				return null;
			}

		});
	}

	private void getTaskFromContainer() {
		S2Container root = this.s2container.getRoot();
		Traversal.forEachComponent(root, new Traversal.ComponentDefHandler() {
			public Object processComponent(ComponentDef componentDef) {
				Class clazz = componentDef.getComponentClass();
				Task task = (Task) clazz.getAnnotation(Task.class);
				if (task != null) {
					CopyOnWriteArrayList<TaskContena> list = getTaskContenaMap(TASK_TYPE_SCHEDULED);
					list.add(new TaskContena(componentDef));
				}
				return null;
			}

		});

	}

	public boolean addTask(Object task) {

		return false;
	}

	public boolean removeTask(Object task) {

		return false;
	}

}

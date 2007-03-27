package org.seasar.chronos.handler.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskStateType;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.util.TaskPropertyUtil;
import org.seasar.framework.container.S2Container;

public class ScheduleExecuteStartHandler extends AbstractScheduleExecuteHandler {

	private S2Container s2container;

	public void setS2Container(S2Container s2container) {
		this.s2container = s2container;
	}

	private Scheduler scheduler;

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	@Override
	public void handleRequest() throws InterruptedException {
		final List<TaskContena> scheduledTaskList = this.taskContenaStateManager
				.getTaskContenaList(TaskStateType.SCHEDULED);
		final List<TaskContena> runingTaskList = this.taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);
		for (final TaskContena tc : scheduledTaskList) {
			final TaskExecutorService tes = (TaskExecutorService) s2container
					.getComponent(TaskExecutorService.class);
			tes.setTaskComponentDef(tc.getComponentDef());
			tes.setGetterSignal(scheduler);
			tes.prepare();
			if (TaskPropertyUtil.getStartTask(tes)) {
				// タスクの開始
				Future<TaskExecutorService> taskStaterFuture = executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								synchronized (runingTaskList) {
									runingTaskList.notify();
								}
								log.debug("initialize start");
								String nextTaskName = tes.initialize();
								log.debug("initialize end");
								if (nextTaskName != null) {
									try {
										log.debug("execute start");
										tes.execute(nextTaskName);
										log.debug("waitOne start");
										tes.waitOne();
									} catch (RejectedExecutionException ex) {
										log.debug(ex);
									}
								}
								log.debug("destory start");
								tes.destroy();
								log.debug("destory end");
								if (runingTaskList.contains(tc)) {
									runingTaskList.remove(tc);
								}
								return tes;
							}
						});
				synchronized (runingTaskList) {
					tc.setTaskStaterFuture(taskStaterFuture);
					tc.setTaskExecutorService(tes);
					runingTaskList.add(tc);
					scheduledTaskList.remove(tc);
					runingTaskList.wait();
					log.debug("Task " + TaskPropertyUtil.getTaskName(tes)
							+ " Start");
				}
			}
		}
	}

}

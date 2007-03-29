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
			final TaskExecutorService tes = tc.getTaskExecutorService();
			if (TaskPropertyUtil.getStartTask(tes)) {
				// タスクの開始
				log.log("DCHRONOSSSTHRT001", new Object[] { TaskPropertyUtil
						.getTaskName(tes) });
				Future<TaskExecutorService> taskStaterFuture = this.executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								TaskExecutorService _tes = tes;
								Object[] logArgs = new Object[] { TaskPropertyUtil
										.getTaskName(_tes) };
								log.log("DCHRONOS000111", logArgs);
								String nextTaskName = _tes.initialize();
								if (nextTaskName != null) {
									try {
										_tes.execute(nextTaskName);
										_tes.waitOne();
									} catch (RejectedExecutionException ex) {
										log.log("ECHRONOS0002", logArgs, ex);
									}
								}
								_tes.destroy();
								if (runingTaskList.contains(tc)) {
									runingTaskList.remove(tc);
								}
								log.log("DCHRONOS000112", logArgs);
								return tes;
							}
						});

				tc.setTaskStaterFuture(taskStaterFuture);

				runingTaskList.add(tc);

				scheduledTaskList.remove(tc);
				break;
			}
		}
	}

}

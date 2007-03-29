package org.seasar.chronos.handler.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.seasar.chronos.impl.SchedulerImpl;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskStateType;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.util.TaskPropertyUtil;

public class ScheduleExecuteShutdownHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		final List<TaskContena> runingTaskList = this.taskContenaStateManager
				.getTaskContenaList(TaskStateType.RUNNING);
		final List<TaskContena> cancelTaskList = this.taskContenaStateManager
				.getTaskContenaList(TaskStateType.CANCELED);
		for (final TaskContena tc : runingTaskList) {
			final TaskExecutorService tes = tc.getTaskExecutorService();
			if (TaskPropertyUtil.getShutdownTask(tes)) {
				log.log("DCHRONOS0011", new Object[] { TaskPropertyUtil
						.getTaskName(tes) });
				final Future<TaskExecutorService> future = this.executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								Object[] logArgs = new Object[] { TaskPropertyUtil
										.getTaskName(tes) };
								log.log("DCHRONOS0002", logArgs);
								log.log("DCHRONOS0012", logArgs);
								if (tes.cancel()) {
									log.debug("cancel ok");
									while (!tes
											.await(
													SchedulerImpl.SHUTDOWN_AWAIT_TIME,
													SchedulerImpl.SHUTDOWN_AWAIT_TIMEUNIT)) {
										log.log("DCHRONOS0013", logArgs);
									}
									if (cancelTaskList.contains(tc)) {
										cancelTaskList.remove(tc);
									}
								} else {
									log.debug("cancel error!");
								}
								log.log("DCHRONOS0014", logArgs);
								log.log("DCHRONOS0003",
										new Object[] { TaskPropertyUtil
												.getTaskName(tes) });
								return tes;
							}
						});
				tc.setTaskStaterFuture(future);
				cancelTaskList.add(tc);
				runingTaskList.remove(tc);
			}
		}
	}

}

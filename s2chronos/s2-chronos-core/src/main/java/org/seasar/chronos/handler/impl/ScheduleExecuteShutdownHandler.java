package org.seasar.chronos.handler.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.seasar.chronos.impl.SchedulerImpl;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskStateType;
import org.seasar.chronos.impl.TaskContenaStateManager.TaskContenaHanlder;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.util.TaskPropertyUtil;

public class ScheduleExecuteShutdownHandler extends
		AbstractScheduleExecuteHandler {

	@Override
	public void handleRequest() throws InterruptedException {
		this.taskContenaStateManager.forEach(TaskStateType.RUNNING,
				new TaskContenaHanlder() {
					public Object processTaskContena(
							final TaskContena taskContena) {
						final TaskExecutorService tes = taskContena
								.getTaskExecutorService();
						if (TaskPropertyUtil.getShutdownTask(tes)) {
							log.log("DCHRONOS0011",
									new Object[] { TaskPropertyUtil
											.getTaskName(tes) });
							final Future<TaskExecutorService> future = executorService
									.submit(new Callable<TaskExecutorService>() {
										public TaskExecutorService call()
												throws Exception {
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
													log.log("DCHRONOS0013",
															logArgs);
												}
												schedulerEventHandler
														.fireCancelTask(tes
																.getTask());
												taskContenaStateManager
														.removeTaskContena(
																TaskStateType.CANCELING,
																taskContena);
												taskContenaStateManager
														.addTaskContena(
																TaskStateType.UNSCHEDULED,
																taskContena);
											} else {
												log.debug("cancel error!");
											}
											log.log("DCHRONOS0014", logArgs);
											log
													.log(
															"DCHRONOS0003",
															new Object[] { TaskPropertyUtil
																	.getTaskName(tes) });
											return tes;
										}
									});
							taskContena.setTaskStaterFuture(future);
							taskContenaStateManager.addTaskContena(
									TaskStateType.CANCELING, taskContena);
							taskContenaStateManager.removeTaskContena(
									TaskStateType.RUNNING, taskContena);

						}
						return null;
					}
				});
	}

}

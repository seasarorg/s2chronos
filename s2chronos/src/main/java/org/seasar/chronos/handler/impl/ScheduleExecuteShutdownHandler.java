package org.seasar.chronos.handler.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

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
				log.debug("shutdownTask on");
				final Future<TaskExecutorService> future = this.executorService
						.submit(new Callable<TaskExecutorService>() {
							public TaskExecutorService call() throws Exception {
								synchronized (runingTaskList) {
									runingTaskList.notify();
								}
								log.debug("cancel start");
								if (tes.cancel()) {
									log.debug("cancel ok");
									if (tes.await(30, TimeUnit.SECONDS) == false) {
										// TODO キャンセルできなかった．例外をスローすること．
									}
									if (cancelTaskList.contains(tc)) {
										cancelTaskList.remove(tc);
									}
								} else {
									runingTaskList.add(tc);
								}
								log.debug("cancel end");
								return tes;
							}
						});
				synchronized (runingTaskList) {
					tc.setFuture(future);
					cancelTaskList.add(tc);
					runingTaskList.remove(tc);
					runingTaskList.wait();
				}
			}
		}
	}

}

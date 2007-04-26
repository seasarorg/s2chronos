package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.SchedulerWrapper;
import org.seasar.chronos.core.impl.TaskContena;
import org.seasar.chronos.core.impl.TaskContenaStateManager;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.logger.Logger;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.util.TaskPropertyUtil;
import org.seasar.chronos.extension.store.ScheduleStore;
import org.seasar.chronos.extension.store.entity.ScheduleEntity;

public class StoredSchedulerWrapperImpl extends SchedulerWrapper {

	private class InternalSchedulerEventListener implements
			SchedulerEventListener {

		public void addTask(Scheduler scheduler, TaskStateType type,
				TaskExecutorService taskExecutorService) {
			storeAddTask(type, taskExecutorService);
		}

		public void cancelTask(Scheduler scheduler, Object task) {
			storeCancelTask(task);

		}

		public void endScheduler(Scheduler scheduler) {
			storeEndScheduler();
		}

		public void endTask(Scheduler scheduler, Object task) {
			storeEndTask(task);
		}

		public void pauseScheduler(Scheduler scheduler) {
			storePauseScheduler();
		}

		public void removeTask(Scheduler scheduler, TaskStateType type,
				Object task) {
			storeRemoveTask(type, task);
		}

		public void removeTask(Scheduler scheduler, TaskStateType type,
				TaskExecutorService taskExecutorService) {
			// TODO 自動生成されたメソッド・スタブ

		}

		public void resigtTaskAfterScheduler(Scheduler scheduler) {
			storeResigtTaskAfterScheduler();
		}

		public void resigtTaskBeforeScheduler(Scheduler scheduler) {
			storeResigtTaskBeforeScheduler();
		}

		public void shutdownScheduler(Scheduler scheduler) {
			storeShutdownScheduler();
		}

		public void startScheduler(Scheduler scheduler) {
			storeStartScheduler();
		}

		public void startTask(Scheduler scheduler, Object task) {
			storeStartTask(task);
		}

		private void storeAddTask(TaskStateType type, Object task) {

		}

	}

	private static final Logger log = Logger
			.getLogger(StoredSchedulerWrapperImpl.class);

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	private ScheduleStore scheduleStore;

	public StoredSchedulerWrapperImpl(Scheduler scheduler) {
		super(scheduler);
		this.addListener(new InternalSchedulerEventListener());
	}

	public void addTask(TaskStateType type,
			TaskExecutorService taskExecutorService) {
		// TODO 作業途中
		TaskContena taskContena = taskContenaStateManager
				.getTaskContena(taskExecutorService.getTask());
		log.debug("<<storeAddTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
		taskContena.getTaskExecutorService().save();

		String taskName = TaskPropertyUtil.getTaskName(taskExecutorService);
		String description = TaskPropertyUtil
				.getDescription(taskExecutorService);
		Long taskId = TaskPropertyUtil.getTaskId(taskExecutorService);
		ScheduleEntity se = new ScheduleEntity();
		se.setTaskName(taskName);
		se.setDescription(description);
		se.setTaskId(taskId);
		se.setStatus(type.ordinal());
	}

	private void recoverySchedule() {
		this.scheduleStore.loadAllTasks();
	}

	@Override
	protected void registTaskFromS2Container() {

	}

	public void setScheduleStore(ScheduleStore scheduleStore) {
		this.scheduleStore = scheduleStore;
	}

	private void storeCancelTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeCancelTask>> : "
				+ taskContena.getTaskClass().getName() + " : "
				+ taskContena.getTask());
	}

	private void storeEndScheduler() {
		log.debug("<<storeEndScheduler>>");
	}

	private void storeEndTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeEndTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
	}

	private void storePauseScheduler() {
		log.debug("<<storePauseScheduler>>");
	}

	private void storeRemoveTask(TaskStateType type, Object task) {
		log.debug("<<storeRemoveTask>>");
	}

	private void storeResigtTaskAfterScheduler() {
		log.debug("<<storeResigtTaskAfterScheduler>>");
		this.recoverySchedule();
	}

	private void storeResigtTaskBeforeScheduler() {
		log.debug("<<storeResigtTaskBeforeScheduler>>");
	}

	private void storeShutdownScheduler() {
		log.debug("<<storeShutdownScheduler>>");
	}

	private void storeStartScheduler() {
		log.debug("<<storeStartScheduler>>");
	}

	private void storeStartTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeStartTask>> : "
				+ taskContena.getTaskClass().getName() + " : "
				+ taskContena.getTask());
	}

}

package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.SchedulerWrapper;
import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.logger.Logger;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.extension.store.ScheduleStore;

public class StoredSchedulerWrapperImpl extends SchedulerWrapper {
	private class InternalSchedulerEventListener implements
			SchedulerEventListener {

		public void addTaskScheduleEntry(Scheduler scheduler,
				TaskScheduleEntry taskScheduleEntry) {
			storeAddTask(taskScheduleEntry);
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

		public void removeTaskScheduleEntry(Scheduler scheduler,
				TaskScheduleEntry taskScheduleEntry) {
			storeRemoveTask(taskScheduleEntry);
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

	}

	private static final Logger log = Logger
			.getLogger(StoredSchedulerWrapperImpl.class);

	private TaskScheduleEntryManager taskScheduleEntryStateManager = TaskScheduleEntryManager
			.getInstance();

	private ScheduleStore scheduleStore;

	public StoredSchedulerWrapperImpl(Scheduler scheduler) {
		super(scheduler);
		this.addListener(new InternalSchedulerEventListener());
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

	private void storeAddTask(TaskScheduleEntry taskScheduleEntry) {
		log.debug("<<storeAddTask>> : "
				+ taskScheduleEntry.getTaskClass().getName() + " : "
				+ taskScheduleEntry.getTask());
		taskScheduleEntry.save();
	}

	private void storeCancelTask(Object task) {
		TaskScheduleEntry taskContena = taskScheduleEntryStateManager
				.getTaskScheduleEntry(task);
		log.debug("<<storeCancelTask>> : "
				+ taskContena.getTaskClass().getName() + " : "
				+ taskContena.getTask());
	}

	private void storeEndScheduler() {
		log.debug("<<storeEndScheduler>>");
	}

	private void storeEndTask(Object task) {
		TaskScheduleEntry taskContena = taskScheduleEntryStateManager
				.getTaskScheduleEntry(task);
		log.debug("<<storeEndTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
	}

	private void storePauseScheduler() {
		log.debug("<<storePauseScheduler>>");
	}

	private void storeRemoveTask(TaskScheduleEntry taskScheduleEntry) {
		log.debug("<<storeRemoveTask>> : "
				+ taskScheduleEntry.getTaskClass().getName() + " : "
				+ taskScheduleEntry.getTask());
		taskScheduleEntry.save();
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
		TaskScheduleEntry taskScheduleEntry = taskScheduleEntryStateManager
				.getTaskScheduleEntry(task);
		log.debug("<<storeStartTask>> : "
				+ taskScheduleEntry.getTaskClass().getName() + " : "
				+ taskScheduleEntry.getTask());
	}

}

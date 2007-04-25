package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerEventListener;
import org.seasar.chronos.core.SchedulerWrapper;
import org.seasar.chronos.core.impl.TaskContena;
import org.seasar.chronos.core.impl.TaskContenaStateManager;
import org.seasar.chronos.core.logger.Logger;
import org.seasar.chronos.extension.store.ScheduleStore;

public class StoredSchedulerWrapperImpl extends SchedulerWrapper {

	private class InternalSchedulerEventListener implements
			SchedulerEventListener {

		public void addTask(Scheduler scheduler, Object task) {
			storeAddTask(task);
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

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	private ScheduleStore scheduleStoreImpl;

	public StoredSchedulerWrapperImpl(Scheduler scheduler) {
		super(scheduler);
		this.addListener(new InternalSchedulerEventListener());
	}

	private void recoverySchedule() {
		this.scheduleStoreImpl.loadAllTasks();
	}

	@Override
	protected void registTaskFromS2Container() {
		// TODO 自動生成されたメソッド・スタブ

	}

	private void storeAddTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeAddTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
		// TODO: OutputObjectStreamでDBに書き出し
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

package org.seasar.chronos.store.impl;

import java.util.List;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerEventListener;
import org.seasar.chronos.impl.SchedulerDecorator;
import org.seasar.chronos.impl.TaskContena;
import org.seasar.chronos.impl.TaskContenaStateManager;
import org.seasar.chronos.impl.TaskStateType;
import org.seasar.chronos.logger.Logger;
import org.seasar.chronos.store.RecoveryTaskInfo;
import org.seasar.chronos.store.ScheduleStore;
import org.seasar.chronos.task.TaskExecutorService;

public class StoredSchedulerDecorator extends SchedulerDecorator {

	private static final Logger log = Logger
			.getLogger(StoredSchedulerDecorator.class);

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	private ScheduleStore scheduleStore;

	public StoredSchedulerDecorator(Scheduler scheduler) {
		super(scheduler);
		this.addListener(new InternalSchedulerEventListener());
	}

	private void storeAddTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeAddTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
		// TODO: OutputObjectStreamでDBに書き出し
	}

	private void storeStartTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeStartTask>> : "
				+ taskContena.getTaskClass().getName() + " : "
				+ taskContena.getTask());
	}

	private void storeCancelTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeCancelTask>> : "
				+ taskContena.getTaskClass().getName() + " : "
				+ taskContena.getTask());
	}

	private void storeEndTask(Object task) {
		TaskContena taskContena = taskContenaStateManager.getTaskContena(task);
		log.debug("<<storeEndTask>> : " + taskContena.getTaskClass().getName()
				+ " : " + taskContena.getTask());
	}

	private void storeResigtTaskBeforeScheduler() {
		log.debug("<<storeResigtTaskBeforeScheduler>>");
	}

	private void storeResigtTaskAfterScheduler() {
		log.debug("<<storeResigtTaskAfterScheduler>>");
		this.recoverySchedule();
	}

	private void recoverySchedule() {
		// DBにSCHEDULED, RUNNINGのものを再度抽出
		List<RecoveryTaskInfo> rtis = scheduleStore.getRecoveryTaskNames();
		if (rtis != null) {
			taskContenaStateManager.allRemove(TaskStateType.SCHEDULED);
		}
		for (RecoveryTaskInfo rti : rtis) {
			TaskContena taskContena = taskContenaStateManager
					.getTaskContena(rti.getTaskName());
			TaskExecutorService tes = taskContena.getTaskExecutorService();
			// タスクIDを指定しなおす
			tes.setTaskId(rti.getTaskId());
			// DBからタスクの情報を抜き出し再設定
			tes.load();
			// 再スケジュール
			this.taskContenaStateManager.addTaskContena(
					TaskStateType.SCHEDULED, taskContena);

		}
	}

	private void storeStartScheduler() {
		log.debug("<<storeStartScheduler>>");
	}

	private void storeEndScheduler() {
		log.debug("<<storeEndScheduler>>");
	}

	private void storeShutdownScheduler() {
		log.debug("<<storeShutdownScheduler>>");
	}

	private void storePauseScheduler() {
		log.debug("<<storePauseScheduler>>");
	}

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

		public void shutdownScheduler(Scheduler scheduler) {
			storeShutdownScheduler();
		}

		public void startScheduler(Scheduler scheduler) {
			storeStartScheduler();
		}

		public void startTask(Scheduler scheduler, Object task) {
			storeStartTask(task);
		}

		public void resigtTaskAfterScheduler(Scheduler scheduler) {
			storeResigtTaskAfterScheduler();
		}

		public void resigtTaskBeforeScheduler(Scheduler scheduler) {
			storeResigtTaskBeforeScheduler();
		}

	}

	@Override
	protected void registTaskFromS2Container() {
		// TODO 自動生成されたメソッド・スタブ

	}

}

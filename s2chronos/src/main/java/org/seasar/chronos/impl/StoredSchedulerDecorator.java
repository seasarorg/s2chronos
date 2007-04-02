package org.seasar.chronos.impl;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.SchedulerEventListener;
import org.seasar.chronos.logger.Logger;

public class StoredSchedulerDecorator extends SchedulerDecorator {

	private static final Logger log = Logger
			.getLogger(StoredSchedulerDecorator.class);

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

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

	private void storePreparedScheduler() {
		log.debug("<<storePreparedScheduler>>");
		// InputObjectStreamでDBからJavaオブジェクトを読み込む
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

		public void preparedScheduler(Scheduler scheduler) {
			storePreparedScheduler();
		}

	}

	@Override
	protected void registTaskFromS2Container() {
		// TODO 自動生成されたメソッド・スタブ

	}

}

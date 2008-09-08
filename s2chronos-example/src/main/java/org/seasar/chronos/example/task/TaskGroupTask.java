package org.seasar.chronos.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.task.method.TaskGroup;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.framework.log.Logger;

/**
 * タスクグループに対応したタスクです．
 * <p>
 * トリガーはNonDelayTriggerです．
 * </p>
 */
@Task
@NonDelayTrigger
public class TaskGroupTask {

	/**
	 * ロガーです．
	 */
	private Logger log = Logger.getLogger(TaskGroupTask.class);

	/**
	 * 開始メソッドです．
	 */
	@NextTask("groupA")
	public void start() {
		log.info("start");
	}

	/**
	 * グループ開始メソッドです．
	 */
	@NextTask("taskA")
	public void startGroupA() {
		log.info("startGroupA");
	}

	/**
	 * タスクAメソッドです．
	 */
	@TaskGroup("groupA")
	@NextTask("taskB")
	public void doTaskA() {
		log.info("doTaskA");
	}

	/**
	 * タスクBメソッドです．
	 */
	@TaskGroup("groupA")
	public void doTaskB() {
		log.info("doTaskB");
	}

	/**
	 * グループ終了メソッドです．
	 */
	public void endGroupA() {
		log.info("endGroupA");
	}

	/**
	 * 終了メソッドです．
	 */
	public void end() {
		log.info("end");
	}

}

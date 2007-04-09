package org.seasar.chronos.smartdeploy.task;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Task(name = "smart")
public class SmartTask {

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		SmartTask src = (SmartTask) obj;
		result = result & trigger.equals(src.trigger);
		return result;
	}

	private static final Logger log = Logger.getLogger(SmartTask.class);

	private TaskTrigger trigger;

	public void setNonDelayTrigger(TaskTrigger trigger) {
		this.trigger = trigger;
	}

	public TaskTrigger getTrigger() {
		return this.trigger;
	}

	// タスクが実行されるときに最初に呼ばれる
	@NextTask("taskA")
	public synchronized void initialize() {
		log.info("SmartTask::initialize");
	}

	// タスクメソッドA 本体
	// 遷移先を静的に設定し，非同期で実行
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info("SmartTask::doTaskA");
	}

	// タスクメソッドB 本体
	// 同期で実行し遷移先を動的に指定する
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info("SmartTask::doTaskB");
		return "taskC";
	}

	// タスクメソッドC 本体
	// 非同期に100個タスクメソッドを生成して実行
	@JoinTask(JoinType.NoWait)
	@CloneTask(10)
	public synchronized void doTaskC() {
		log.info("<<SmartTask::doTaskC");
	}

	// すべてのタスクが終了したら呼ばれる
	@NextTask("example")
	public synchronized void destroy() {
		log.info("SmartTask::destroy");
	}

}

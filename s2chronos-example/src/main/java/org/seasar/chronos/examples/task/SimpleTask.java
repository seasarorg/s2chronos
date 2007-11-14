package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Task(name = "example")
public class SimpleTask {

	private static Logger log = Logger.getLogger(SimpleTask.class);

	private boolean startTask = true;
	private boolean endTask = false;

	// trueを返すとタスクの起動が開始される
	public boolean isStartTask() {
		return this.startTask;
	}

	public boolean isEndTask() {
		return endTask;
	}

	// タスクが実行されるときに最初に呼ばれる
	@NextTask("taskA")
	public synchronized void initialize() {
		log.info("[[SimpleTask::initialize]]");
		endTask = true;
	}

	// タスクメソッドA 本体
	// 遷移先を静的に設定し，非同期で実行
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info("[[SimpleTask::doTaskA]]");
	}

	// タスクメソッドB 本体
	// 同期で実行し遷移先を動的に指定する
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info("[[SimpleTask::doTaskB]]");
		return "taskC";
	}

	// タスクメソッドC 本体
	// 非同期に100個タスクメソッドを生成して実行
	@JoinTask(JoinType.NoWait)
	@CloneTask(10)
	public synchronized void doTaskC() {
		log.info("[[SimpleTask::doTaskC]]");
	}

	// すべてのタスクが終了したら呼ばれる
	// @NextTask("example")
	public synchronized void destroy() {
		log.info("[[SimpleTask::destroy]]");
	}

}

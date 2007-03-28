package org.seasar.chronos.task;

import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.annotation.task.method.CloneTask;
import org.seasar.chronos.annotation.task.method.JoinTask;
import org.seasar.chronos.annotation.task.method.NextTask;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Task(name = "simple2")
public class SimpleTask2 {

	private static Logger log = Logger.getLogger(SimpleTask2.class);

	private boolean startTask = true;

	// trueを返すとタスクの起動が開始される
	public synchronized boolean getStartTask() {
		return this.startTask;
	}

	// タスクが実行されるときに最初に呼ばれる
	@NextTask("taskA")
	public synchronized void initialize() {
		log.info("SimpleTask2::initialize");
	}

	// タスクメソッドA 本体
	// 遷移先を静的に設定し，非同期で実行
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info("SimpleTask2::doTaskA");
	}

	// タスクメソッドB 本体
	// 同期で実行し遷移先を動的に指定する
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info("SimpleTask2::doTaskB");
		return "taskC";
	}

	// タスクメソッドC 本体
	// 非同期に100個タスクメソッドを生成して実行
	@JoinTask(JoinType.NoWait)
	@CloneTask(20)
	public synchronized void doTaskC() {
		log.info(">>SimpleTask2::doTaskC");
	}

	// すべてのタスクが終了したら呼ばれる
	// @NextTask("example")
	public synchronized void destroy() {
		log.info("SimpleTask2::destroy");
	}

}

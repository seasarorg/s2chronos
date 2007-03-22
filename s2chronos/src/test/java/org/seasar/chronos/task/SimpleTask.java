package org.seasar.chronos.task;

import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.annotation.task.method.CloneTask;
import org.seasar.chronos.annotation.task.method.JoinTask;
import org.seasar.chronos.annotation.task.method.NextTask;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Task
public class SimpleTask {

	private static Logger log = Logger.getLogger(SimpleTask.class);

	private boolean startTask = true;

	// trueを返すとタスクの起動が開始される
	public boolean getStartTask() {
		return this.startTask;
	}

	// タスクが実行されるときに最初に呼ばれる
	@NextTask("groupA")
	public void initialize() {
		log.info("initialize");
	}

	// タスクメソッドA 本体
	// 遷移先を静的に設定し，非同期で実行
	@NextTask("jobB")
	@JoinTask(JoinType.NoWait)
	public void doJobA() {
		log.info("doJobA");
	}

	// タスクメソッドB 本体
	// 同期で実行し遷移先を動的に指定する
	@JoinTask(JoinType.Wait)
	public String doJobB() {
		log.info("doJobB");
		if (System.currentTimeMillis() % 2 == 0) {
			return "jobA";
		}
		return "jobC";
	}

	// タスクメソッドC 本体
	// 非同期に100個タスクメソッドを生成して実行
	@JoinTask(JoinType.NoWait)
	@CloneTask(100)
	public void doJobC() {
		log.info("doJobC");
	}

	// すべてのタスクが終了したら呼ばれる
	public void destroy() {
		log.info("destroy");
	}

}

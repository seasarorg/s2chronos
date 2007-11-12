package org.seasar.chronos.core.task;

import java.io.Serializable;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.Cron;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Task
@Cron(expression = "*/1 * * * *")
public class SimpleTask implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient static Logger log = Logger.getLogger(SimpleTask.class);

	// private TaskTrigger trigger = new CronTrigger("*/1 * * * *");
	//
	// public TaskTrigger getTrigger() {
	// return trigger;
	//	}

	// タスクが実行されるときに最初に呼ばれる
	@NextTask("taskA")
	public synchronized void initialize() {
		log.info("[[initializeを実行しました．]]");
	}

	// タスクメソッドA 本体
	// 遷移先を静的に設定し，非同期で実行
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info("[[doTaskAを実行しました．]]");
	}

	// タスクメソッドB 本体
	// 同期で実行し遷移先を動的に指定する
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info("[[doTaskBを実行しました．]]");
		// throw new RuntimeException();
		return "taskC";
	}

	// タスクメソッドC 本体
	// 非同期に10個タスクメソッドを生成して実行
	@JoinTask(JoinType.NoWait)
	@CloneTask(10)
	public synchronized void doTaskC() {
		log.info("[[doTaskCを実行しました．]]");
	}

	// すべてのタスクが終了したら呼ばれる
	// @NextTask("example")
	public synchronized void destroy() {
		log.info("[[destroyを実行しました．]]");
	}

}

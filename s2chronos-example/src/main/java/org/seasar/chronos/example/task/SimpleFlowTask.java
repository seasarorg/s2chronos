package org.seasar.chronos.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

/**
 * Simpleタスクです．
 * <p>
 * NonDelayTriggerアノテーションの場合は，即時実行のトリガーとなります．
 * </p>
 * 
 * @author junichi
 * 
 */
@Task
@NonDelayTrigger
public class SimpleFlowTask {

	/**
	 * ロガーです．
	 */
	private static Logger log = Logger.getLogger(SimpleFlowTask.class);

	/**
	 * タスクが実行されるときに最初に呼ばれます．
	 * <p>
	 * 次のタスクはdoTaskAメソッドになります．
	 * </p>
	 */
	@NextTask("taskA")
	public void initialize() {
		log.info("[[SimpleFlowTask::initialize]]");
	}

	/**
	 * タスクメソッドAです．
	 * <p>
	 * JoinTaskアノテーションがNoWaitの場合は本メソッドは非同期に実行されます． 次のタスクはdoTaskBメソッドになります．
	 * </p>
	 */
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info("[[SimpleFlowTask::doTaskA]]");
	}

	/**
	 * タスクメソッドBです．
	 * <p>
	 * JoinTaskアノテーションがWaitの場合は本メソッドは同期的に実行されます． 次のタスクは，戻り値で設定されます．
	 * </p>
	 * 
	 * @return 次のタスク名
	 */
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info("[[SimpleFlowTask::doTaskB]]");
		return "taskC";
	}

	/**
	 * タスクメソッドCです．
	 * <p>
	 * JoinTaskアノテーションがNoWaitの場合は本メソッドは非同期に実行されます． CloneTaskで本メソッドを並列処理数を指定できます．
	 * 次のタスクはありません．
	 * </p>
	 */
	@JoinTask(JoinType.NoWait)
	@CloneTask(10)
	public synchronized void doTaskC() {
		log.info("[[SimpleFlowTask::doTaskC]]");
	}

	/**
	 * すべてのタスクメソッドが終了したら呼ばれます．
	 */
	public synchronized void destroy() {
		log.info("[[SimpleFlowTask::destroy]]");
	}

}

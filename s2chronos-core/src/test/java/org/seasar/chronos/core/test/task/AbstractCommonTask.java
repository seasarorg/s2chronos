package org.seasar.chronos.core.test.task;

import org.seasar.chronos.core.annotation.task.method.CloneTask;
import org.seasar.chronos.core.annotation.task.method.JoinTask;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

public abstract class AbstractCommonTask {

	private transient static Logger log = Logger.getLogger(SimpleTask.class);

	/**
	 * 初期化メソッドです。
	 * <p>
	 * タスククラスが初期化されるとき一度だけに呼ばれます。
	 * </p>
	 */
	public synchronized void initialize() {
		log.info(this.getClass().getName() + " : [[initializeを実行しました．]]");
	}

	/**
	 * 開始メソッドです。
	 * <p>
	 * タスクメソッドが実行される前に呼ばれます。<br>
	 * 開始メソッドが存在しない場合はdoExecuteを探してあれば実行します。
	 * </p>
	 */
	@NextTask("taskA")
	public synchronized void start() {
		log.info(this.getClass().getName() + " : [[startを実行しました．]]");
	}

	/**
	 * タスクメソッドAです。
	 * <p>
	 * 非同期に実行します。
	 * </p>
	 */
	@NextTask("taskB")
	@JoinTask(JoinType.NoWait)
	public synchronized void doTaskA() {
		log.info(this.getClass().getName() + " : [[doTaskAを実行しました．]]");
	}

	/**
	 * タスクメソッドBです。
	 * <p>
	 * 同期で実行します。
	 * </p>
	 * 
	 * @return 次に遷移するタスク名
	 */
	@JoinTask(JoinType.Wait)
	public synchronized String doTaskB() {
		log.info(this.getClass().getName() + " : [[doTaskBを実行しました．]]");
		// throw new RuntimeException();
		return "taskC";
	}

	/**
	 * タスクCです。
	 * <p>
	 * 非同期に実行します。CloneTaskアノテーションで20同時並行処理を行います。
	 * </p>
	 */
	@JoinTask(JoinType.NoWait)
	@CloneTask(20)
	public synchronized void doTaskC() {
		log.info(this.getClass().getName() + " : [[doTaskCを実行しました．]]");
	}

	/**
	 * 終了メソッドです。
	 */
	public synchronized void finish() {
		log.info(this.getClass().getName() + " : [[finishを実行しました．]]");
	}

	/**
	 * 破棄メソッドです。
	 * <p>
	 * タスクが破棄されるときに実行されるメソッドです。ただし、isReScheduleTaskｔがtrueを返すタスクは呼ばれません
	 * </p>
	 */
	public void destroy() {
		log.info(this.getClass().getName() + " : [[destroyを実行しました．]]");
	}
}

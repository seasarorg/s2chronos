package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.task.Task;
import org.seasar.chronos.annotation.task.method.Join;
import org.seasar.chronos.annotation.task.method.NextTask;
import org.seasar.chronos.annotation.task.method.TaskGroup;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.chronos.trigger.Trigger;
import org.seasar.chronos.trigger.impl.GenericTrigger;
import org.seasar.framework.log.Logger;

@Task
public class ExampleTask {

	private Logger log = Logger.getLogger(ExampleTask.class);

	// ジョブメソッドをどのタイプのスレッドプールで実行するか返します
	public ThreadPoolType getThreadPoolType() {
		return ThreadPoolType.FIXED;
	}

	// FIXEDの場合は、プールサイズを返します。
	// ex)2とした場合同時に2スレッドまでの多重度を指定可能。
	public int getThreadPoolSize() {
		return 2;
	}

	// エントリメソッド
	// 最初に実行するジョブもしくはジョブグループを指定します。
	@NextTask("groupA")
	public void initialize() {
		log.info("initialize");
	}

	// ------------------- JOB GROUP A
	// ジョブグループが開始したときに呼ばれます
	// @Next("jobA")
	// public void startGroupA() {
	// log.info("startGroupA");
	// }

	@TaskGroup("groupA")
	@NextTask("jobA")
	public void doHoge() {
		log.info("doHoge");
	}

	// ジョブメソッドA
	@TaskGroup("groupA")
	@NextTask("jobB")
	@Join(JoinType.NoWait)
	public void doJobA() throws Exception {

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {

		}

		// this.terminate = true;

	}

	// ジョブメソッドB
	@TaskGroup("groupA")
	@Join(JoinType.NoWait)
	public void doJobB() {
		for (int i = 1; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				break;
			}
			log.info("doJobB");
		}
	}

	// ジョブグループが終了したときに呼ばれます
	// @Next("groupB")
	public void endGroupA() {
		log.info("endGroupA");
	}

	// ジョブメソッドC
	// @TaskGroup("groupB")
	@NextTask("groupB")
	public void doJobC() {
		log.info("doJobC");
	}

	// ------------------- JOB GROUP B
	// ジョブグループが開始したときに呼ばれます
	@NextTask("jobD")
	public void startGroupB() {
		log.info("startGroupB");
	}

	// ジョブメソッドD
	@TaskGroup("groupB")
	public void doJobD() {
		log.info("doJobD");
	}

	// ジョブグループが終了したときに呼ばれます
	@NextTask("jobE")
	public void endGroupB() {
		log.info("endGroupB");
	}

	// ------------------- JOB E
	// ジョブE
	@NextTask("jobF")
	public void doJobE() {
		log.info("doJobE");
	}

	// ------------------- JOB F
	// ジョブF
	public void doJobF() {
		log.info("doJobF");
	}

	// ジョブが破棄されるときに呼ばれます
	public void destroy() {
		log.info("destroy");
	}

	// ジョブで例外がスローされると呼ばれます
	public void cancel() {
		log.info("cancel");
	}

	private boolean executed = false;

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public boolean isExecuted() {
		return executed;
	}

	private boolean startTask;

	// 実行したらfalseにします．
	public void setStartTask(boolean startTask) {
		this.startTask = startTask;
	}

	// trueを返すとスケジューラから起動されます
	public boolean getStartTask() {
		return this.startTask;
	}

	private boolean endTask;

	// 停止したらfalseにします．
	public void setEndTask(boolean endTask) {
		this.endTask = endTask;
	}

	// trueを返すとスケジューラから停止されます．
	public boolean getEndTask() {
		return endTask;
	}

	private boolean shutdownTask;

	// シャットダウンしたらfalseにします．
	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	// trueを返すとスケジューラからシャットダウンされます．
	public boolean getShutdownTask() {
		return this.shutdownTask;
	}

	private GenericTrigger trigger;

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(GenericTrigger trigger) {
		this.trigger = trigger;
	}

	public void startScheduler() {
		log.info("startScheduler");
	}

	public void shutdownScheduler() {
		log.info("shutdownScheduler");
	}

}

package org.seasar.chronos.task;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.job.Job;
import org.seasar.chronos.annotation.job.method.Join;
import org.seasar.chronos.annotation.job.method.NextTask;
import org.seasar.chronos.annotation.job.method.TaskGroup;
import org.seasar.chronos.annotation.type.JoinType;
import org.seasar.framework.log.Logger;

@Job
public class ExampleJob {

	private Logger log = Logger.getLogger(ExampleJob.class);

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

		// throw new Exception();

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

	public boolean isExecuted() {
		return executed;
	}

	// 実行可能かを返す
	public boolean canExecute() {
		if (!executed) {
			executed = true;
			return true;
		}
		return false;
	}

	public boolean canTerminate() {
		if (executed) {
			executed = false;
			return true;
		}
		return false;
	}

	public void startScheduler() {
		log.info("startScheduler");
	}

	public void shutdownScheduler() {
		log.info("shutdownScheduler");
	}

}

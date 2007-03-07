package org.seasar.s2chronos.job;

import org.seasar.framework.log.Logger;
import org.seasar.s2chronos.ThreadPoolType;
import org.seasar.s2chronos.annotation.job.Job;
import org.seasar.s2chronos.annotation.job.method.Group;
import org.seasar.s2chronos.annotation.job.method.Join;
import org.seasar.s2chronos.annotation.job.method.Next;
import org.seasar.s2chronos.annotation.type.JoinType;

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
	@Next("groupA")
	public void initialize() {
		log.info("initialize");
	}

	// ------------------- JOB GROUP A
	// メソッドグループが開始したときに呼ばれます
	@Next("jobA")
	public void startGroupA() {
		log.info("startGroupA");
	}

	// ジョブメソッドA
	@Group("groupA")
	@Next("jobB")
	@Join(JoinType.NoWait)
	public void doJobA() {
		for (int i = 1; i < 100; i++) {
			log.info("doJobA");
		}
	}

	// ジョブメソッドB
	@Group("groupA")
	@Join(JoinType.NoWait)
	public void doJobB() {
		for (int i = 1; i < 100; i++) {
			log.info("doJobB");
		}
	}

	// メソッドグループが終了したときに呼ばれます
	@Next("groupB")
	public void endGroupA() {
		log.info("endGroupA");
	}

	// ------------------- JOB GROUP B
	// メソッドグループが開始したときに呼ばれます
	@Next("jobC")
	public void startGroupB() {
		log.info("startGroupB");
	}

	// ジョブメソッドC
	@Group("groupB")
	@Next("jobD")
	@Join(JoinType.NoWait)
	public void doJobC() {
		log.info("doJobC");
	}

	// ジョブメソッドD
	@Group("groupB")
	@Join(JoinType.NoWait)
	public void doJobD() {
		log.info("doJobD");
	}

	// メソッドグループが終了したときに呼ばれます
	@Next("jobE")
	public void endGroupB() {
		log.info("endGroupB");
	}

	// ------------------- JOB E
	// ジョブE
	@Next("jobF")
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

	// 実行可能かを返す
	public boolean canExecute() {
		return true;
	}

	public void startScheduler() {
		log.info("startScheduler");
	}

	public void shutdownScheduler() {
		log.info("shutdownScheduler");
	}

}

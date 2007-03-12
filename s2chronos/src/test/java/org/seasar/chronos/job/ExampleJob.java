package org.seasar.chronos.job;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.annotation.job.Job;
import org.seasar.chronos.annotation.job.method.Group;
import org.seasar.chronos.annotation.job.method.Join;
import org.seasar.chronos.annotation.job.method.Next;
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
	@Next("groupA")
	public void initialize() {
		log.info("initialize");
	}

	// ------------------- JOB GROUP A
	// ジョブグループが開始したときに呼ばれます
	@Next("jobA")
	public void startGroupA() {
		log.info("startGroupA");
	}

	// ジョブメソッドA
	@Group("groupA")
	@Next("jobB")
	@Join(JoinType.NoWait)
	public void doJobA() throws Exception {

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}

		throw new Exception();

	}

	// ジョブメソッドB
	@Group("groupA")
	@Join(JoinType.NoWait)
	public void doJobB() {
		for (int i = 1; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
			log.info("doJobB");
		}
	}

	// ジョブグループが終了したときに呼ばれます
	@Next("groupB")
	public void endGroupA() {
		log.info("endGroupA");
	}

	// ------------------- JOB GROUP B
	// ジョブグループが開始したときに呼ばれます
	@Next("jobC")
	public void startGroupB() {
		log.info("startGroupB");
	}

	// ジョブメソッドC
	@Group("groupB")
	@Next("jobD")
	public void doJobC() {
		log.info("doJobC");
	}

	// ジョブメソッドD
	@Group("groupB")
	public void doJobD() {
		log.info("doJobD");
	}

	// ジョブグループが終了したときに呼ばれます
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

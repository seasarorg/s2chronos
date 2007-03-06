package org.seasar.s2chronos.job;

import org.seasar.framework.log.Logger;
import org.seasar.s2chronos.annotation.job.Job;
import org.seasar.s2chronos.annotation.job.method.Group;
import org.seasar.s2chronos.annotation.job.method.Next;

@Job
public class ExampleJob {

	private Logger log = Logger.getLogger(ExampleJob.class);

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
	public void doJobA() {
		log.info("doJobA");
	}

	// ジョブメソッドB
	@Group("groupA")
	public void doJobB() {
		log.info("doJobB");
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
	public void doJobC() {
		log.info("doJobC");
	}

	// ジョブメソッドD
	@Group("groupB")
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

	public boolean canExecute() {
		return true;
	}

}

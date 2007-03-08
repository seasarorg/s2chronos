package org.seasar.s2chronos.job;

import org.seasar.s2chronos.ThreadPoolType;
import org.seasar.s2chronos.annotation.job.method.Group;
import org.seasar.s2chronos.annotation.job.method.Join;
import org.seasar.s2chronos.annotation.job.method.Next;
import org.seasar.s2chronos.annotation.type.JoinType;

public interface ExampleJob {

	// ジョブメソッドをどのタイプのスレッドプールで実行するか返します
	public abstract ThreadPoolType getThreadPoolType();

	// FIXEDの場合は、プールサイズを返します。
	// ex)2とした場合同時に2スレッドまでの多重度を指定可能。
	public abstract int getThreadPoolSize();

	// エントリメソッド
	// 最初に実行するジョブもしくはジョブグループを指定します。
	@Next("groupA")
	public abstract void initialize();

	// ------------------- JOB GROUP A
	// ジョブグループが開始したときに呼ばれます
	@Next("jobA")
	public abstract void startGroupA();

	// ジョブメソッドA
	@Group("groupA")
	@Next("jobB")
	@Join(JoinType.NoWait)
	public abstract void doJobA() throws Exception;

	// ジョブメソッドB
	@Group("groupA")
	@Join(JoinType.NoWait)
	public abstract void doJobB();

	// ジョブグループが終了したときに呼ばれます
	@Next("groupB")
	public abstract void endGroupA();

	// ------------------- JOB GROUP B
	// ジョブグループが開始したときに呼ばれます
	@Next("jobC")
	public abstract void startGroupB();

	// ジョブメソッドC
	@Group("groupB")
	@Next("jobD")
	public abstract void doJobC();

	// ジョブメソッドD
	@Group("groupB")
	public abstract void doJobD();

	// ジョブグループが終了したときに呼ばれます
	@Next("jobE")
	public abstract void endGroupB();

	// ------------------- JOB E
	// ジョブE
	@Next("jobF")
	public abstract void doJobE();

	// ------------------- JOB F
	// ジョブF
	public abstract void doJobF();

	// ジョブが破棄されるときに呼ばれます
	public abstract void destroy();

	// ジョブで例外がスローされると呼ばれます
	public abstract void cancel();

	// 実行可能かを返す
	public abstract boolean canExecute();

	public abstract void startScheduler();

	public abstract void shutdownScheduler();

}
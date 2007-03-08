package org.seasar.s2chronos.job.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.framework.log.Logger;
import org.seasar.s2chronos.ThreadPoolType;
import org.seasar.s2chronos.annotation.job.Job;
import org.seasar.s2chronos.annotation.job.method.Group;
import org.seasar.s2chronos.annotation.job.method.Join;
import org.seasar.s2chronos.annotation.job.method.Next;
import org.seasar.s2chronos.annotation.type.JoinType;

@Job
public class ExampleJobImpl {

	private Logger log = Logger.getLogger(ExampleJobImpl.class);

	// ジョブメソッドをどのタイプのスレッドプールで実行するか返します
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#getThreadPoolType()
	 */
	public ThreadPoolType getThreadPoolType() {
		return ThreadPoolType.CACHED;
	}

	// FIXEDの場合は、プールサイズを返します。
	// ex)2とした場合同時に2スレッドまでの多重度を指定可能。
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#getThreadPoolSize()
	 */
	public int getThreadPoolSize() {
		return 2;
	}

	// エントリメソッド
	// 最初に実行するジョブもしくはジョブグループを指定します。
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#initialize()
	 */
	@Next("groupA")
	public void initialize() {
		log.info("initialize");
	}

	// ------------------- JOB GROUP A
	// ジョブグループが開始したときに呼ばれます
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#startGroupA()
	 */
	@Next("jobA")
	public void startGroupA() {
		log.info("startGroupA");
	}

	// ジョブメソッドA
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobA()
	 */
	@Group("groupA")
	@Next("jobB")
	@Join(JoinType.NoWait)
	public void doJobA() throws Exception {
		for (int i = 1; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				break;
			}
			log.info("doJobA");
		}
	}

	// ジョブメソッドB
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobB()
	 */
	@Group("groupA")
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
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#endGroupA()
	 */
	@Next("groupB")
	public void endGroupA() {
		log.info("endGroupA");
	}

	// ------------------- JOB GROUP B
	// ジョブグループが開始したときに呼ばれます
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#startGroupB()
	 */
	@Next("jobC")
	public void startGroupB() {
		log.info("startGroupB");
	}

	// ジョブメソッドC
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobC()
	 */
	@Group("groupB")
	@Next("jobD")
	public void doJobC() {
		log.info("doJobC");
	}

	// ジョブメソッドD
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobD()
	 */
	@Group("groupB")
	public void doJobD() {
		log.info("doJobD");
	}

	// ジョブグループが終了したときに呼ばれます
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#endGroupB()
	 */
	@Next("jobE")
	public void endGroupB() {
		log.info("endGroupB");
	}

	// ------------------- JOB E
	// ジョブE
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobE()
	 */
	@Next("jobF")
	public void doJobE() {
		log.info("doJobE");
	}

	// ------------------- JOB F
	// ジョブF
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#doJobF()
	 */
	public void doJobF() {
		log.info("doJobF");
	}

	// ジョブが破棄されるときに呼ばれます
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#destroy()
	 */
	public void destroy() {
		log.info("destroy");
	}

	// ジョブで例外がスローされると呼ばれます
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#cancel()
	 */
	public void cancel() {
		log.info("cancel");
	}

	// 実行可能かを返す
	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#canExecute()
	 */
	public boolean canExecute() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#startScheduler()
	 */
	public void startScheduler() {
		log.info("startScheduler");
	}

	/* (non-Javadoc)
	 * @see org.seasar.s2chronos.job.impl.ExampleJob#shutdownScheduler()
	 */
	public void shutdownScheduler() {
		log.info("shutdownScheduler");
	}

}

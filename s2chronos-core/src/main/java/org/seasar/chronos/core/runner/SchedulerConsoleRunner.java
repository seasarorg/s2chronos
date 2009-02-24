package org.seasar.chronos.core.runner;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerRunner;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * コンソールで{@link Scheduler}を簡単に起動するためのランナークラスです．
 * 
 * @author j5ik2o
 */
public class SchedulerConsoleRunner implements SchedulerRunner {
	/**
	 * スケジューラを起動します．
	 */
	public void run() {
		SingletonS2ContainerFactory.init();
		SingletonS2Container.getComponent(Scheduler.class).process();
		SingletonS2ContainerFactory.destroy();
	}

	/**
	 * mainメソッドです．
	 * 
	 * @param arg
	 *            プラグラム引数
	 * @return 0=正常終了, -1=異常終了
	 */
	public static int main(String[] arg) {
		SchedulerConsoleRunner runner = new SchedulerConsoleRunner();
		try {
			runner.run();
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
		return 0;
	}
}

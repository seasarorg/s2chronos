package org.seasar.chronos.example.main;

import org.seasar.chronos.core.Scheduler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * スケジューラを起動するためのサンプルクラスです．
 * 
 * @author junichi
 * 
 */
public class Sample {

	/**
	 * メインメソッドです．
	 * <p>
	 * スケジューラのインスタンスを取得し，スケジューラを開始します．
	 * </p>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SingletonS2ContainerFactory.init();
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Scheduler scheduler = (Scheduler) container
				.getComponent(Scheduler.class);
		scheduler.start();
		try {
			scheduler.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SingletonS2ContainerFactory.destroy();
	}

}

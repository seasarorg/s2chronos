package org.seasar.chronos.example.main;

import org.seasar.chronos.core.Scheduler;
import org.seasar.framework.container.SingletonS2Container;
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
		SingletonS2Container.getComponent(Scheduler.class).process();
		SingletonS2ContainerFactory.destroy();
	}
}

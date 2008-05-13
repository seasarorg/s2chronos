package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.Scheduler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class ProfileApplication {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SingletonS2ContainerFactory.setConfigPath("test.dicon");
		SingletonS2ContainerFactory.init();
		S2Container container = SingletonS2ContainerFactory.getContainer();

		Scheduler scheduler = (Scheduler) container
				.getComponent(Scheduler.class);
		scheduler.start();
		scheduler.join();
		SingletonS2ContainerFactory.destroy();
	}

}

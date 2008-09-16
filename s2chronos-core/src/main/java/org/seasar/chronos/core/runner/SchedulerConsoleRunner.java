package org.seasar.chronos.core.runner;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.SchedulerRunner;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class SchedulerConsoleRunner implements SchedulerRunner {

	public void run() {
		SingletonS2ContainerFactory.init();
		SingletonS2Container.getComponent(Scheduler.class).process();
		SingletonS2ContainerFactory.destroy();
	}

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

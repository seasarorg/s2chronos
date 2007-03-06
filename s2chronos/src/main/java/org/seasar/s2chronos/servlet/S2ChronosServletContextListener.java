package org.seasar.s2chronos.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.framework.log.Logger;
import org.seasar.s2chronos.Scheduler;
import org.seasar.s2chronos.exception.SchedulerException;

public class S2ChronosServletContextListener implements ServletContextListener {

	private static Logger log = Logger
			.getLogger(S2ChronosServletContextListener.class);

	private static Scheduler scheduler;

	public static Scheduler getScheduler() {
		return S2ChronosServletContextListener.scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		S2ChronosServletContextListener.scheduler = scheduler;
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			S2ChronosServletContextListener.scheduler.shutdown();
			S2ChronosServletContextListener.scheduler.join();
		} catch (SchedulerException e) {
			log.error(e);
		}
	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
			S2ChronosServletContextListener.scheduler.start();
		} catch (SchedulerException e) {
			log.error(e);
		}
	}

}

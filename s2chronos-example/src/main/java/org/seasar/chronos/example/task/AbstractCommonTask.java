package org.seasar.chronos.example.task;

import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.framework.log.Logger;

public abstract class AbstractCommonTask {

	private static Logger log = Logger.getLogger(AbstractCommonTask.class);

	public void initialize() {
		log.info("[[" + this.getClass().getSimpleName() + ":initialize]]");
	}

	@NextTask("taskA")
	public synchronized void start() {
		log.info("[[" + this.getClass().getSimpleName() + ":start]]");
	}

	public synchronized void doTaskA() {
		log.info("[[" + this.getClass().getSimpleName() + ":doTaskA]]");
	}

	public synchronized void end() {
		log.info("[[" + this.getClass().getSimpleName() + ":end]]");
	}

	public void destroy() {
		log.info("[[" + this.getClass().getSimpleName() + ":destroy]]");
	}

}

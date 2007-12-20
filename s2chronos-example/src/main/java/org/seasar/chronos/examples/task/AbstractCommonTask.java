package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.framework.log.Logger;

public abstract class AbstractCommonTask {

	private static Logger log = Logger.getLogger(AbstractCommonTask.class);

	@NextTask("taskA")
	public synchronized void initialize() {
		log.info(this.getClass().getSimpleName() + ":initialize");
	}

	public synchronized void doTaskA() {
		log.info(this.getClass().getSimpleName() + ":doTaskA");
	}

	public synchronized void destroy() {
		log.info(this.getClass().getSimpleName() + ":destroy");
	}

}

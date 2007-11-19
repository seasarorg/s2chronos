package org.seasar.chronos.examples.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.examples.annotation.ExampleTrigger;
import org.seasar.framework.log.Logger;

@Task
@ExampleTrigger(div = 3)
public class ExampleTriggerTask {

	private static Logger log = Logger.getLogger(ExampleTriggerTask.class);

	@NextTask("taskA")
	public synchronized void initialize() {
		log.info("[[ExampleTriggerTask::initialize]]");
	}

	public synchronized void doTaskA() {
		log.info("[[ExampleTriggerTask::doTaskA]]");
	}

	public synchronized void destroy() {
		log.info("[[ExampleTriggerTask::destroy]]");
	}

}

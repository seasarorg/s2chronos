package org.seasar.chronos.core.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.task.TestTask;

public class SchedulerImplTest extends S2TestCaseBase {

	public void testStart() {

		Scheduler scheduler = (Scheduler) this.getComponent("scheduler");

		Class<?> taskClass = this.getComponentDef(TestTask.class)
				.getComponentClass();

		scheduler.addTask(taskClass);

		scheduler.start();
		scheduler.join();

	}

}

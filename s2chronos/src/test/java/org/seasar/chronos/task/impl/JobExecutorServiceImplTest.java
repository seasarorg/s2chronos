package org.seasar.chronos.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.ExampleTask;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;

public class JobExecutorServiceImplTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private TaskExecutorService taskExecutorService;

	private ComponentDef jobComponentDef;

	protected void setUp() throws Exception {
		include(PATH);
		this.taskExecutorService = (TaskExecutorService) this
				.getComponent(TaskExecutorService.class);
		this.jobComponentDef = this.getComponentDef(ExampleTask.class);
	}

	public void testAwait() {
		try {
			String startTaskName = this.taskExecutorService
					.initialize(jobComponentDef);
			this.taskExecutorService.execute(startTaskName);
			this.taskExecutorService.await(10, TimeUnit.SECONDS);
			this.taskExecutorService.destroy();
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testExecute() {
		try {
			String startTaskName = this.taskExecutorService
					.initialize(jobComponentDef);
			this.taskExecutorService.execute(startTaskName);
			this.taskExecutorService.destroy();
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testCancel() {
		try {
			this.taskExecutorService.initialize(jobComponentDef);
		} catch (InterruptedException e) {
			fail();
		}
		this.taskExecutorService.cancel();
	}

	public void testDestroy() {
		try {
			this.taskExecutorService.initialize(jobComponentDef);
			this.taskExecutorService.destroy();
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testInitialize() {
		try {
			this.taskExecutorService.initialize(jobComponentDef);
		} catch (InterruptedException e) {
			fail();
		}
	}

}

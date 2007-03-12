package org.seasar.chronos.job.impl;

import org.seasar.chronos.job.ExampleJob;
import org.seasar.chronos.job.JobExecuteStrategy;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;

public class JobBeanDescTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private JobExecuteStrategy jobExecuteStrategyImpl;

	private ComponentDef jobComponentDef;

	protected void setUp() throws Exception {
		include(PATH);
		jobExecuteStrategyImpl = (JobExecuteStrategy) getComponent(JobExecuteStrategyImpl.class);
		jobComponentDef = this.getComponentDef(ExampleJob.class);
	}

	public void testInitialize() throws Throwable {
		this.jobExecuteStrategyImpl.initialize(jobComponentDef);
	}

	public void testCallJob() throws Throwable {
		String startTaskName = this.jobExecuteStrategyImpl.initialize(jobComponentDef);
		this.jobExecuteStrategyImpl.callJob(startTaskName);
		this.jobExecuteStrategyImpl.destroy();
	}

	public void testDestroy() throws Throwable {
		this.jobExecuteStrategyImpl.initialize(jobComponentDef);
		this.jobExecuteStrategyImpl.destroy();
	}

	public void testCanExecute() throws Throwable {
		this.jobExecuteStrategyImpl.initialize(jobComponentDef);
		boolean result = this.jobExecuteStrategyImpl.canExecute();
		assertNotNull(result);
		this.jobExecuteStrategyImpl.destroy();
	}

	public void testGetThreadPoolSize() throws Throwable {
		this.jobExecuteStrategyImpl.initialize(jobComponentDef);
		int result = this.jobExecuteStrategyImpl.getThreadPoolSize();
		assertEquals(result > 0, true);
		this.jobExecuteStrategyImpl.destroy();
	}

	public void testGetThreadPoolType() throws Throwable {
		this.jobExecuteStrategyImpl.initialize(jobComponentDef);
		this.jobExecuteStrategyImpl.getThreadPoolType();
		this.jobExecuteStrategyImpl.destroy();
	}

}

package org.seasar.chronos.job;

import java.util.concurrent.ExecutionException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;

public class JobExecutorService_ExampleJobTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private JobExecutorService target;

	protected void setUp() throws Exception {

		this.include(PATH);

		this.target = (JobExecutorService) this
				.getComponent(JobExecutorService.class);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialize() throws Throwable {

		assertNotNull(this.target);

		ComponentDef jobDef = this.getComponentDef(ExampleJob.class);
		this.target.initialize(jobDef);

	}

	public void testCallJob() throws Throwable {
		ComponentDef jobDef = this.getComponentDef(ExampleJob.class);
		String jobName = this.target.initialize(jobDef);
		try {
			this.target.callJob(jobName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			this.target.cancel();
		}
		this.target.destroy();
	}

	public void testDestroy() throws Throwable {
		ComponentDef jobDef = this.getComponentDef(ExampleJob.class);
		this.target.initialize(jobDef);
		this.target.destroy();

	}

	public void testCanExecute() throws Throwable {
		ComponentDef jobDef = this.getComponentDef(ExampleJob.class);
		this.target.initialize(jobDef);
		try {
			this.target.canExecute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

}

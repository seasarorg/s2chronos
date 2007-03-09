package org.seasar.s2chronos.job;

import java.util.concurrent.ExecutionException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;
import org.seasar.s2chronos.exception.InvalidNextJobMethodException;

public class JobExecutorService_ExampleJobTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private JobExecutorService target;

	protected void setUp() throws Exception {

		this.include(PATH);

		this.target = (JobExecutorService) this
				.getComponent(JobExecutorService.class);

		ComponentDef jobDef = this.getComponentDef(ExampleJob.class);

		this.target.setJobComponentDef(jobDef);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialize() throws InterruptedException, ExecutionException {

		assertNotNull(this.target);
		this.target.initialize();

	}

	public void testCallJob() throws InvalidNextJobMethodException,
			InterruptedException, ExecutionException {

		String jobName = this.target.initialize();
		try {
			this.target.callJob(jobName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			this.target.cancel();
		}
		this.target.destroy();
	}

	public void testDestroy() throws InterruptedException, ExecutionException {

		this.target.destroy();

	}

	public void testCanExecute() throws InterruptedException, ExecutionException {
		this.target.initialize();
		try {
			this.target.canExecute();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

}

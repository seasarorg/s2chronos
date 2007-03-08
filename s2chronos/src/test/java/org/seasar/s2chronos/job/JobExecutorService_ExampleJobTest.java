package org.seasar.s2chronos.job;

import java.util.concurrent.ExecutionException;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.s2chronos.exception.InvalidNextJobMethodException;
import org.seasar.s2chronos.job.impl.ExampleJobImpl;

public class JobExecutorService_ExampleJobTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private JobExecutorService target;

	protected void setUp() throws Exception {

		this.include(PATH);
		ExampleJobImpl job = (ExampleJobImpl) this
				.getComponent(ExampleJobImpl.class);
		this.target = (JobExecutorService) this
				.getComponent(JobExecutorService.class);

		this.target.setJob(job);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialize() {

		assertNotNull(this.target);
		this.target.initialize();

	}

	public void testCallJob() throws InvalidNextJobMethodException,
			ExecutionException {

		String jobName = this.target.initialize();
		try {
			this.target.callJob(jobName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.target.destroy();
	}

	public void testDestroy() {

		this.target.destroy();

	}

	public void testCanExecute() {

		this.target.canExecute();

	}

}

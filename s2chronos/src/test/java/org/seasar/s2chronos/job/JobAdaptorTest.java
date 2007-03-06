package org.seasar.s2chronos.job;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.log.Logger;

public class JobAdaptorTest extends S2TestCase {

	private Logger log = Logger.getLogger(JobAdaptorTest.class);

	private static final String PATH = "app.dicon";

	private JobAdaptor target;

	protected void setUp() throws Exception {

		this.include(PATH);
		ExampleJob job = (ExampleJob) this.getComponent(ExampleJob.class);
		this.target = new JobAdaptor(job);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testInitialize() {

		this.target = new JobAdaptor(new ExampleJob());
		assertNotNull(this.target);
		this.target.initialize();

	}

	public void testCallJob() {

		String jobName = this.target.initialize();
		do {
			JobAdaptor.ResultSet resultSet = this.target.callJob(jobName);
			jobName = resultSet.getResult();
		} while (jobName != null);

	}

	public void testDestroy() {

		this.target.destroy();

	}

	public void testCanExecute() {

		this.target.canExecute();

	}

}

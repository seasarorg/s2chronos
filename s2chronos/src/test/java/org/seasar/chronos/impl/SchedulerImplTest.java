package org.seasar.chronos.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.exception.SchedulerException;
import org.seasar.extension.unit.S2TestCase;

public class SchedulerImplTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private Scheduler scheduler;

	protected void setUp() throws Exception {
		include(PATH);
		scheduler = (Scheduler) this.getComponent(Scheduler.class);
	}

	// public void testStart() {
	// try {
	// scheduler.start();
	// scheduler.join();
	// } catch (SchedulerException e) {
	// fail();
	// } catch (InterruptedException e) {
	// ;
	// }
	// }

	public void testShutdown() {
		try {
			scheduler.start();
			TimeUnit.SECONDS.sleep(5);
			scheduler.shutdown();
			scheduler.join();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO é©ìÆê∂ê¨Ç≥ÇÍÇΩ catch ÉuÉçÉbÉN
			e.printStackTrace();
		}

	}

}

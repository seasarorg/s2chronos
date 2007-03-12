package org.seasar.chronos.job;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.annotation.job.method.Next;
import org.seasar.framework.log.Logger;

public class InterruptSupportJob {

	private Logger log = Logger.getLogger(InterruptSupportJob.class);

	@Next("jobA")
	public void initialize() {
		log.info("initialize");
	}

	public void doJobA() {
		int count = 0;
		try {
			while (true) {
				TimeUnit.SECONDS.sleep(1);
				log.info(count++);
			}
		} catch (InterruptedException e) {
		}
	}

	// ƒWƒ‡ƒu‚ª”jŠü‚³‚ê‚é‚Æ‚«‚ÉŒÄ‚Î‚ê‚Ü‚·
	public void destroy() {
		log.info("destroy");
	}
}

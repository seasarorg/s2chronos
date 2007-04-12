package org.seasar.chronos.core.delegate;

import org.seasar.framework.log.Logger;

public class InvokeTarget {

	private Logger log = Logger.getLogger(InvokeTarget.class);

	public void testA() {

	}

	public String testB(final String value) {
		return value;
	}

	public void testC() {
		try {
			while (true) {
				log.info("testC");
				Thread.sleep(2);
			}
		} catch (InterruptedException e) {
			log.info(e);
		}

	}

}

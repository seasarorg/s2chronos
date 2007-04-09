package org.seasar.chronos.trigger;

import org.seasar.chronos.S2TestCaseBase;

public class TimedTriggerTest extends S2TestCaseBase {

	private TimedTrigger timedTrigger;

	public void testSave() {
		this.timedTrigger.save();
	}

	public void testLoad() {
		this.timedTrigger.load();
	}

}

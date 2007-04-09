package org.seasar.chronos.trigger;

import org.seasar.chronos.S2TestCaseBase;
import org.seasar.chronos.core.trigger.TimedTrigger;

public class TimedTriggerTest extends S2TestCaseBase {

	private TimedTrigger timedTrigger;

	public void testSave() {
		this.timedTrigger.save();
	}

	public void testLoad() {
		this.timedTrigger.load();
	}

}

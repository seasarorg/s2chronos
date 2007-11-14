package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.trigger.CTimedTrigger;

public class TimedTriggerTest extends S2TestCaseBase {

	private CTimedTrigger timedTrigger;

	public void testSave() {
		this.timedTrigger.save();
	}

	public void testLoad() {
		this.timedTrigger.load();
	}

}

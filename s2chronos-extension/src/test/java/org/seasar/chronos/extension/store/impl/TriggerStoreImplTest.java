package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.trigger.NonDelayTrigger;
import org.seasar.chronos.extension.store.TriggerStore;

public class TriggerStoreImplTest extends S2TestCaseBase {

	private TriggerStore triggerStore;

	public void testLoadFromStoreLong() {
		TaskTrigger trigger1 = new NonDelayTrigger();
		Long id = triggerStore.saveToStore(trigger1);
		TaskTrigger trigger2 = triggerStore.loadFromStore(id);
		assertEquals(trigger1, trigger2);
	}

	public void testLoadFromStoreLongTaskTrigger() {
		TaskTrigger trigger1 = new NonDelayTrigger();
		Long id = triggerStore.saveToStore(trigger1);
		TaskTrigger trigger2 = new NonDelayTrigger();
		triggerStore.loadFromStore(id, trigger2);
		assertEquals(trigger1, trigger2);
	}

	public void testSaveToStore() {
		TaskTrigger trigger = new NonDelayTrigger();
		triggerStore.saveToStore(trigger);
	}

}

package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.S2TestCaseBase;

public class ScheduleStoreTest extends S2TestCaseBase {

	private ScheduleStore scheduleStore;

	public void testLoadAllTasks() {
		scheduleStore.loadAllTasks();
	}

	public void testSaveAllTasks() {
		scheduleStore.saveAllTasks();
	}

}

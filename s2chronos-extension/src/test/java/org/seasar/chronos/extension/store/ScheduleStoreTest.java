package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.S2TestCaseBase;

public class ScheduleStoreTest extends S2TestCaseBase {

	private ScheduleStore scheduleStoreImpl;

	public void testLoadAllTasks() {
		scheduleStoreImpl.loadAllTasks();
	}

	public void testSaveAllTasks() {
		scheduleStoreImpl.saveAllTasks();
	}

}

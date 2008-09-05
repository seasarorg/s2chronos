package org.seasar.chronos.sastruts.example.task;

import org.junit.Test;

public class EventExecuteTaskTest {

	private EventExecuteTask eventExecuteTask;

	public void postBindFields() {
	}

	public void preUnbindFields() {
	}

	@Test
	public void testDoExecute() {
		eventExecuteTask.doExecute();
	}

}

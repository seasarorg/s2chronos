package org.seasar.chronos.sastruts.example.task;

import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class SessionTimerTaskTest {

	private SessionTimerTask sessionTimerTask;

	public void testDoExecute() {
		sessionTimerTask.doExecute();
	}

}

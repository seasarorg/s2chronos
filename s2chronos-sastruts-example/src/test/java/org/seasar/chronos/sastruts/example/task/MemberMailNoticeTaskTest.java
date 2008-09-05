package org.seasar.chronos.sastruts.example.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class MemberMailNoticeTaskTest {

	private MemberMailNoticeTask memberMailNoticeTask;

	@Test
	public void testStart() {
		memberMailNoticeTask.initialize();
		memberMailNoticeTask.start();
		memberMailNoticeTask.doRegisterMail();
	}

}

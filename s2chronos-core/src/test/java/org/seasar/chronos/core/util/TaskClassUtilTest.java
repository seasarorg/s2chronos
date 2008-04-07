package org.seasar.chronos.core.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskClassUtilTest {

	@Test
	public void testIsTask_NormalTask() {
		assertEquals("0", TaskClassUtil.isTask(NormalTask.class));
	}

	@Test
	public void testIsTask_IsNotATask() {
		assertEquals("0", !TaskClassUtil.isTask(NormalTask.class));
	}

}

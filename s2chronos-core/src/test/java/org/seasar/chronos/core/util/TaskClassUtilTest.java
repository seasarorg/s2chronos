package org.seasar.chronos.core.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskClassUtilTest {

	@Test
	public void testIsTask_NormalATask() {
		// assertTrue(TaskClassUtil.isTask(NormalATask.class));
	}

	@Test
	public void testIsTask_NormalBTask() {
		// assertTrue(TaskClassUtil.isTask(NormalBTask.class));
	}

	@Test
	public void testIsTask_IsNotATask() {
		// assertFalse(TaskClassUtil.isTask(IsNotATask.class));
	}

	@Test
	public void testIsTask_IsNotBTask() {
		// assertFalse(TaskClassUtil.isTask(IsNotBTask.class));
	}

	@Test
	public void testIsTask_IsNotCTask() {
		// assertFalse(TaskClassUtil.isTask(IsNotCTask.class));
	}

}

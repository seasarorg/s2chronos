package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.task.TaskValidator;
import org.seasar.chronos.core.util.IsNotATask;
import org.seasar.chronos.core.util.IsNotBTask;
import org.seasar.chronos.core.util.IsNotCTask;
import org.seasar.chronos.core.util.NormalATask;
import org.seasar.chronos.core.util.NormalBTask;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskValidatorImplTest {

	private TaskValidator taskValidator;

	@Test
	public void testIsTask_NormalATask() {
		assertTrue(this.taskValidator.isValid(NormalATask.class));
	}

	@Test
	public void testIsTask_NormalBTask() {
		assertTrue(this.taskValidator.isValid(NormalBTask.class));
	}

	@Test
	public void testIsTask_IsNotATask() {
		assertFalse(this.taskValidator.isValid(IsNotATask.class));
	}

	@Test
	public void testIsTask_IsNotBTask() {
		assertFalse(this.taskValidator.isValid(IsNotBTask.class));
	}

	@Test
	public void testIsTask_IsNotCTask() {
		assertFalse(this.taskValidator.isValid(IsNotCTask.class));
	}
}

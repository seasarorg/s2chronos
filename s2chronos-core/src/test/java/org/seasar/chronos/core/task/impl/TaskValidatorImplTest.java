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
	private final NormalATask task = new NormalATask();

	@Test
	public void testIsTask_NormalATask() {
		assertTrue(this.taskValidator.isValid(this.task, NormalATask.class));
	}

	@Test
	public void testIsTask_NormalBTask() {
		assertTrue(this.taskValidator.isValid(this.task, NormalBTask.class));
	}

	@Test
	public void testIsTask_IsNotATask() {
		assertFalse(this.taskValidator.isValid(this.task, IsNotATask.class));
	}

	@Test
	public void testIsTask_IsNotBTask() {
		assertFalse(this.taskValidator.isValid(this.task, IsNotBTask.class));
	}

	@Test
	public void testIsTask_IsNotCTask() {
		assertFalse(this.taskValidator.isValid(this.task, IsNotCTask.class));
	}
}

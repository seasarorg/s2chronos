package org.seasar.chronos.core.task;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.util.NormalATask;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskValidatorImplTest {

	private TaskValidator taskValidator;

	@Test
	public void testIsValid() {
		NormalATask task = new NormalATask();
		boolean result = taskValidator.isValid(task, NormalATask.class);
		assertTrue(result);
	}

}

package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.trigger.CNonDelayTrigger;
import org.seasar.chronos.core.util.NormalATask;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskPropertyWriterImplTest {

	private TaskPropertyWriter taskPropertyWriter;

	private final NormalATask testTask = new NormalATask();

	@Test
	public void testSetThreadPoolType() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setThreadPoolType(ThreadPoolType.CACHED);
			assertEquals(this.testTask.getThreadPoolType(),
					ThreadPoolType.CACHED);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetDescription() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setDescription("description");
			assertEquals(this.testTask.getDescription(), "description");
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetEndTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setEndTask(false);
			assertEquals(this.testTask.isEndTask(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetExecuted() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setExecuted(false);
			assertEquals(this.testTask.isExecuted(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetReSchedule() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setReSchedule(false);
			assertEquals(this.testTask.isReSchedule(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetShutdownTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setShutdownTask(false);
			assertEquals(this.testTask.isShutdownTask(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetStartTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setStartTask(false);
			assertEquals(this.testTask.isStartTask(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetTaskId() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setTaskId(1L);
			assertEquals(this.testTask.getTaskId(), 1L);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetTaskName() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setTaskName("taskName");
			assertEquals(this.testTask.getTaskName(), "taskName");
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetThreadPoolSize() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setThreadPoolSize(1);
			assertEquals(this.testTask.getThreadPoolSize(), 1);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetTrigger() {
		try {
			CNonDelayTrigger trigger = new CNonDelayTrigger();
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.setTrigger(trigger);
			assertEquals(this.testTask.getTrigger(), trigger);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasDescription() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasDescription();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasEndTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasEndTask();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasExecuted() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasExecuted();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasReSchedule() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasReSchedule();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasShutdownTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasShutdownTask();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasStartTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasStartTask();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasTaskId() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasTaskId();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasTaskName() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasTaskName();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasThreadPoolSize() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasThreadPoolSize();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasThreadPoolType() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasThreadPoolType();
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testHasTrigger() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, NormalATask.class);
			this.taskPropertyWriter.hasTrigger();
		} catch (Exception ex) {
			fail();
		}
	}

}
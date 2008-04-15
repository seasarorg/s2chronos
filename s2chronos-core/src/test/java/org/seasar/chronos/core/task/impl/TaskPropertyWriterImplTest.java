package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.trigger.CNonDelayTrigger;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskPropertyWriterImplTest {

	private TaskPropertyWriter taskPropertyWriter;

	private TestTask testTask = new TestTask();

	@Test
	public void testSetThreadPoolType() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
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
			this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
			this.taskPropertyWriter.setDescription("description");
			assertEquals(this.testTask.getDescription(), "description");
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetEndTask() {
		try {
			this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
			this.taskPropertyWriter.setEndTask(false);
			assertEquals(this.testTask.isEndTask(), false);
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void testSetExecuted() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setExecuted(false);
		assertEquals(this.testTask.isExecuted(), false);
	}

	@Test
	public void testSetReSchedule() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setReSchedule(false);
		assertEquals(this.testTask.isReSchedule(), false);
	}

	@Test
	public void testSetShutdownTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setShutdownTask(false);
		assertEquals(this.testTask.isShutdownTask(), false);
	}

	@Test
	public void testSetStartTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setStartTask(false);
		assertEquals(this.testTask.isStartTask(), false);
	}

	@Test
	public void testSetTaskId() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setTaskId(1L);
		assertEquals(this.testTask.getTaskId(), 1L);
	}

	@Test
	public void testSetTaskName() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setTaskName("taskName");
		assertEquals(this.testTask.getTaskName(), "taskName");
	}

	@Test
	public void testSetThreadPoolSize() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setThreadPoolSize(1);
		assertEquals(this.testTask.getThreadPoolSize(), 1);
	}

	@Test
	public void testSetTrigger() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		CNonDelayTrigger trigger = new CNonDelayTrigger();
		this.taskPropertyWriter.setTrigger(trigger);
		assertEquals(this.testTask.getTrigger(), trigger);
	}

	@Test
	public void testHasDescription() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasDescription();
	}

	@Test
	public void testHasEndTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasEndTask();
	}

	@Test
	public void testHasExecuted() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasExecuted();
	}

	@Test
	public void testHasReSchedule() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasReSchedule();
	}

	@Test
	public void testHasShutdownTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasShutdownTask();
	}

	@Test
	public void testHasStartTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasStartTask();
	}

	@Test
	public void testHasTaskId() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasTaskId();
	}

	@Test
	public void testHasTaskName() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasTaskName();
	}

	@Test
	public void testHasThreadPoolSize() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasThreadPoolSize();
	}

	@Test
	public void testHasThreadPoolType() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasThreadPoolType();
	}

	@Test
	public void testHasTrigger() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.hasTrigger();
	}

}

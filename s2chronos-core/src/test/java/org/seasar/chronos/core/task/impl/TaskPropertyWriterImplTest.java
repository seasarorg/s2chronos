package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.NonDelayTrigger;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.trigger.CNonDelayTrigger;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskPropertyWriterImplTest {

	private TaskPropertyWriter taskPropertyWriter;

	@Task
	@NonDelayTrigger
	private class TestTask {

		private String description;
		private ThreadPoolType threadPoolType;
		private int threadPoolSize;

		public ThreadPoolType getThreadPoolType() {
			return this.threadPoolType;
		}

		public void setThreadPoolType(ThreadPoolType threadPoolType) {
			this.threadPoolType = threadPoolType;
		}

		public int getThreadPoolSize() {
			return this.threadPoolSize;
		}

		public void setThreadPoolSize(int threadPoolSize) {
			this.threadPoolSize = threadPoolSize;
		}

		public String getDescription() {
			return this.description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

	}

	private final TestTask testTask = new TestTask();

	@Test
	public void testSetThreadPoolType() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setThreadPoolType(ThreadPoolType.CACHED);
		assertEquals(this.testTask.getThreadPoolType(), ThreadPoolType.CACHED);
	}

	@Test
	public void testSetDescription() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setDescription("description");
		assertEquals(this.testTask.getDescription(), "description");
	}

	@Test
	public void testSetEndTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setEndTask(false);
	}

	@Test
	public void testSetExecuted() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setExecuted(false);
	}

	@Test
	public void testSetReSchedule() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setReSchedule(false);
	}

	@Test
	public void testSetShutdownTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setShutdownTask(false);
	}

	@Test
	public void testSetStartTask() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setStartTask(false);
	}

	@Test
	public void testSetTaskId() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setTaskId(1L);
	}

	@Test
	public void testSetTaskName() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setTaskName("taskName");
	}

	@Test
	public void testSetThreadPoolSize() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setThreadPoolSize(1);
	}

	@Test
	public void testSetTrigger() {
		this.taskPropertyWriter.loadTask(this.testTask, TestTask.class);
		this.taskPropertyWriter.setTrigger(new CNonDelayTrigger());
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

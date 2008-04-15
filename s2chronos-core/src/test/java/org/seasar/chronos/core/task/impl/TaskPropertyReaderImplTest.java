package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskPropertyReaderImplTest {

	private TaskPropertyReader taskPropertyReader;

	private final TestTask testTask = new TestTask();

	@Test
	public void testGetDescription() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		String result = taskPropertyReader.getDescription("test");
		assertEquals(result, testTask.getDescription());
	}

	@Test
	public void testGetTaskId() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		Long result = taskPropertyReader.getTaskId(0L);
		assertEquals(result, testTask.getTaskId());
	}

	@Test
	public void testGetTaskName() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		String result = taskPropertyReader.getTaskName("taskName");
		assertEquals(result, testTask.getTaskName());
	}

	@Test
	public void testGetThreadPoolSize() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		int result = taskPropertyReader.getThreadPoolSize(1);
		assertEquals(result, testTask.getThreadPoolSize());
	}

	@Test
	public void testGetThreadPoolType() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		ThreadPoolType result = taskPropertyReader
				.getThreadPoolType(ThreadPoolType.CACHED);
		assertEquals(result, testTask.getThreadPoolType());
	}

	@Test
	public void testGetTrigger() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		TaskTrigger result = taskPropertyReader.getTrigger(null);
		assertEquals(result, testTask.getTrigger());
	}

	@Test
	public void testIsEndTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.isEndTask(false);
		assertEquals(result, testTask.isEndTask());
	}

	@Test
	public void testIsExecuted() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.isExecuted(false);
		assertEquals(result, testTask.isExecuted());
	}

	@Test
	public void testIsReSchedule() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.isReSchedule(false);
		assertEquals(result, testTask.isReSchedule());
	}

	@Test
	public void testIsShutdownTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.isShutdownTask(false);
		assertEquals(result, testTask.isShutdownTask());
	}

	@Test
	public void testIsStartTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.isStartTask(false);
		assertEquals(result, testTask.isStartTask());
	}

	@Test
	public void testHasDescription() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasDescription();
		assertTrue(result);
	}

	@Test
	public void testHasTaskId() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasTaskId();
		assertTrue(result);
	}

	@Test
	public void testHasTaskName() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasTaskName();
		assertTrue(result);
	}

	@Test
	public void testHasThreadPoolSize() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasThreadPoolSize();
		assertTrue(result);
	}

	@Test
	public void testHasThreadPoolType() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasThreadPoolType();
		assertTrue(result);
	}

	@Test
	public void testHasTrigger() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasTrigger();
		assertTrue(result);
	}

	@Test
	public void testHasEndTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasEndTask();
		assertTrue(result);
	}

	@Test
	public void testHasExecuted() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasExecuted();
		assertTrue(result);
	}

	@Test
	public void testHasReSchedule() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasReSchedule();
		assertTrue(result);
	}

	@Test
	public void testHasShutdownTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasShutdownTask();
		assertTrue(result);
	}

	@Test
	public void testHasStartTask() {
		taskPropertyReader.loadTask(testTask, TestTask.class);
		boolean result = taskPropertyReader.hasStartTask();
		assertTrue(result);
	}

}

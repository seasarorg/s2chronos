package org.seasar.chronos.core.task.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.model.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.util.NormalATask;
import org.seasar.framework.unit.Seasar2;

@RunWith(Seasar2.class)
public class TaskPropertyReaderImplTest {

	private TaskPropertyReader taskPropertyReader;

	private final NormalATask testTask = new NormalATask();

	public void postBindFields() {
		taskPropertyReader.setup(testTask, NormalATask.class);
	}

	@Test
	public void testGetDescription() {
		String result = taskPropertyReader.getDescription("test");
		assertEquals(result, testTask.getDescription());
	}

	@Test
	public void testGetTaskId() {
		Long result = taskPropertyReader.getTaskId(0L);
		assertEquals(result, testTask.getTaskId());
	}

	@Test
	public void testGetTaskName() {
		String result = taskPropertyReader.getTaskName(null);
		assertEquals(result, "taskName");
	}

	@Test
	public void testGetThreadPoolSize() {
		int result = taskPropertyReader.getThreadPoolSize(1);
		assertEquals(result, testTask.getThreadPoolSize());
	}

	@Test
	public void testGetThreadPoolType() {
		ThreadPoolType result = taskPropertyReader
				.getThreadPoolType(ThreadPoolType.CACHED);
		assertEquals(result, testTask.getThreadPoolType());
	}

	@Test
	public void testGetTrigger() {

		TaskTrigger result = taskPropertyReader.getTrigger(null);
		assertEquals(result, testTask.getTrigger());
	}

	@Test
	public void testIsEndTask() {

		boolean result = taskPropertyReader.isEndTask(false);
		assertEquals(result, testTask.isEndTask());
	}

	@Test
	public void testIsExecuted() {

		boolean result = taskPropertyReader.isExecuted(false);
		assertEquals(result, testTask.isExecuted());
	}

	@Test
	public void testIsReSchedule() {
		boolean result = taskPropertyReader.isReScheduleTask(false);
		assertEquals(result, testTask.isReScheduleTask());
	}

	@Test
	public void testIsShutdownTask() {
		boolean result = taskPropertyReader.isShutdownTask(false);
		assertEquals(result, testTask.isShutdownTask());
	}

	@Test
	public void testIsStartTask() {
		boolean result = taskPropertyReader.isStartTask(false);
		assertEquals(result, testTask.isStartTask());
	}

	@Test
	public void testHasDescription() {
		boolean result = taskPropertyReader.hasDescription();
		assertTrue(result);
	}

	@Test
	public void testHasTaskId() {
		boolean result = taskPropertyReader.hasTaskId();
		assertTrue(result);
	}

	@Test
	public void testHasTaskName() {
		boolean result = taskPropertyReader.hasTaskName();
		assertTrue(result);
	}

	@Test
	public void testHasThreadPoolSize() {
		boolean result = taskPropertyReader.hasThreadPoolSize();
		assertTrue(result);
	}

	@Test
	public void testHasThreadPoolType() {
		boolean result = taskPropertyReader.hasThreadPoolType();
		assertTrue(result);
	}

	@Test
	public void testHasTrigger() {
		boolean result = taskPropertyReader.hasTrigger();
		assertTrue(result);
	}

	@Test
	public void testHasEndTask() {
		boolean result = taskPropertyReader.hasEndTask();
		assertTrue(result);
	}

	@Test
	public void testHasExecuted() {
		boolean result = taskPropertyReader.hasExecuted();
		assertTrue(result);
	}

	@Test
	public void testHasReScheduleTask() {
		boolean result = taskPropertyReader.hasReScheduleTask();
		assertTrue(result);
	}

	@Test
	public void testHasShutdownTask() {
		boolean result = taskPropertyReader.hasShutdownTask();
		assertTrue(result);
	}

	@Test
	public void testHasStartTask() {
		boolean result = taskPropertyReader.hasStartTask();
		assertTrue(result);
	}

}

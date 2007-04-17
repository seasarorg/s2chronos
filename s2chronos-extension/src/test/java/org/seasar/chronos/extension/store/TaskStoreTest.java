package org.seasar.chronos.extension.store;

import org.seasar.chronos.core.S2TestCaseBase;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskExecutorService;

public class TaskStoreTest extends S2TestCaseBase {

	@Task
	public class TestTask {
		public String initialize() {
			System.out.println("Task#initialize");
			return null;
		}
	}

	private TestTask task = new TestTask();

	private TaskStore taskStore;

	private TaskExecutorService taskExecutorService;

	@Override
	protected void setUpAfterBindFields() throws Throwable {
		taskExecutorService.setTask(task);
		taskExecutorService.setTaskClass(Task.class);
		taskExecutorService.prepare();
	}

	public void testLoadFromStore() {

	}

	public void testSaveToStore() {
		taskStore.saveToStore(taskExecutorService);
	}

}

package org.seasar.chronos.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.impl.TaskExecuteStateNonInitialized;
import org.seasar.framework.container.ComponentDef;

public class TaskExecutorServiceImpl implements TaskExecutorService {

	private TaskExecuteContext taskExecuteContext;

	private TaskExecuteStateNonInitialized taskExecuteStateNonInitialized;

	public TaskExecutorServiceImpl() {

	}

	public void setJobExecuteStateNonInitialized(
			TaskExecuteStateNonInitialized taskExecuteStateNonInitialized) {
		this.taskExecuteStateNonInitialized = taskExecuteStateNonInitialized;
	}

	public void setJobExecuteContext(TaskExecuteContext taskExecuteContext) {
		this.taskExecuteContext = taskExecuteContext;

	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return taskExecuteContext.await(time, timeUnit);
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.taskExecuteContext.execute(startTaskName);
	}

	public boolean canExecute() throws InterruptedException {
		return this.taskExecuteContext.canExecute();
	}

	public void cancel() {
		this.taskExecuteContext.cancel();
	}

	public void destroy() throws InterruptedException {
		this.taskExecuteContext.destroy();
	}

	public String initialize(ComponentDef jobComponentDef)
			throws InterruptedException {
		this.taskExecuteContext.changeState(this.taskExecuteStateNonInitialized);
		return this.taskExecuteContext.initialize(jobComponentDef);
	}

}

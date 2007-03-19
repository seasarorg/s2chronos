package org.seasar.chronos.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.impl.TaskExecuteStateNonInitialized;
import org.seasar.chronos.trigger.Trigger;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecutorServiceImpl implements TaskExecutorService {

	private TaskExecuteContext taskExecuteContext;

	private TaskExecuteStateNonInitialized taskExecuteStateNonInitialized;

	public TaskExecutorServiceImpl() {

	}

	public void setTaskExecuteStateNonInitialized(
			TaskExecuteStateNonInitialized taskExecuteStateNonInitialized) {
		this.taskExecuteStateNonInitialized = taskExecuteStateNonInitialized;

	}

	public void setTaskExecuteContext(TaskExecuteContext taskExecuteContext) {
		this.taskExecuteContext = taskExecuteContext;

	}

	@Binding(bindingType = BindingType.NONE)
	public void setTaskComponentDef(ComponentDef taskComponentDef) {
		this.taskExecuteContext.setTaskComponentDef(taskComponentDef);
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return taskExecuteContext.await(time, timeUnit);
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.taskExecuteContext.execute(startTaskName);
	}

	public boolean cancel() {
		return this.taskExecuteContext.cancel();
	}

	public void destroy() throws InterruptedException {
		this.taskExecuteContext.destroy();
	}

	public void prepare() {
		this.taskExecuteContext
				.changeState(this.taskExecuteStateNonInitialized);
		this.taskExecuteContext.prepare();
	}

	public String initialize() throws InterruptedException {
		return this.taskExecuteContext.initialize();
	}

	public boolean getEndTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getEndTask();
	}

	public boolean getShutdownTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.getShutdownTask();
	}

	public boolean getStartTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getStartTask();
	}

	public int getThreadPoolSize() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.getThreadPoolSize();
	}

	public ThreadPoolType getThreadPoolType() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.getThreadPoolType();
	}

	public Trigger getTrigger() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTrigger();
	}

	public void setEndTask(boolean endTask) {
		this.taskExecuteContext.getTaskExecuteStrategy().setEndTask(endTask);
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.taskExecuteContext.getTaskExecuteStrategy().setShutdownTask(
				shutdownTask);
	}

	public void setStartTask(boolean startTask) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setStartTask(startTask);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(Trigger trigger) {
		this.taskExecuteContext.getTaskExecuteStrategy().setTrigger(trigger);
	}

	public void waitOne() throws InterruptedException {
		this.taskExecuteContext.waitOne();

	}

}

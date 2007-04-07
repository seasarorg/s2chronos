package org.seasar.chronos.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.task.TaskExecutorService;
import org.seasar.chronos.task.state.TaskExecuteContext;
import org.seasar.chronos.task.state.impl.TaskExecuteContextImpl;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecutorServiceImpl implements TaskExecutorService {

	private static final long serialVersionUID = 59104659363668777L;

	private TaskExecuteContext taskExecuteContext = new TaskExecuteContextImpl();

	public TaskExecutorServiceImpl() {

	}

	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteContext.setGetterSignal(getterSignal);
	}

	public void setTaskId(int taskId) {
		this.taskExecuteContext.getTaskExecuteStrategy().setTaskId(taskId);
	}

	public int getTaskId() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskId();
	}

	public String getTaskName() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskName();
	}

	public void setTaskClass(Class taskClass) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setTaskClass(taskClass);
	}

	public Class getTaskClass() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskClass();
	}

	public void setTask(Object task) {
		this.taskExecuteContext.getTaskExecuteStrategy().setTask(task);
	}

	public Object getTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTask();
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

	public String destroy() throws InterruptedException {
		return this.taskExecuteContext.destroy();
	}

	public void prepare() {
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

	public TaskTrigger getTrigger() {
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

	// @Binding(bindingType = BindingType.NONE)
	// public void setTrigger(TaskTrigger taskTrigger) {
	// this.taskExecuteContext.getTaskExecuteStrategy()
	// .setTrigger(taskTrigger);
	// }

	public TaskThreadPool getThreadPool() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getThreadPool();
	}

	public void setThreadPool(TaskThreadPool taskThreadPool) {
		this.taskExecuteContext.getTaskExecuteStrategy().setThreadPool(
				taskThreadPool);
	}

	public void waitOne() throws InterruptedException {
		this.taskExecuteContext.waitOne();
	}

	public Scheduler getScheduler() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getScheduler();
	}

	public void setScheduler(Scheduler scheduler) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setScheduler(scheduler);
	}

	public boolean isExecute() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isExecute();
	}

	public void setExecute(boolean executed) {
		this.taskExecuteContext.getTaskExecuteStrategy().setExecute(executed);
	}

	public void load() {
		this.taskExecuteContext.getTaskExecuteStrategy().load();
	}

	public void save() {
		this.taskExecuteContext.getTaskExecuteStrategy().save();
	}

	public void setTrigger(TaskTrigger taskTrigger) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setTrigger(taskTrigger);
	}

}

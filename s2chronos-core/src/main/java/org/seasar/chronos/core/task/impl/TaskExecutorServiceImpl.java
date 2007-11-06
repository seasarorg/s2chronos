package org.seasar.chronos.core.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecutorServiceImpl implements TaskExecutorService {

	private static final long serialVersionUID = 59104659363668777L;

	private TaskExecuteContext taskExecuteContext;

	public TaskExecutorServiceImpl(TaskExecuteContext taskExecuteContext) {
		this.taskExecuteContext = taskExecuteContext;
	}

	public boolean isReSchedule() {
		return taskExecuteContext.getTaskExecuteStrategy().isReSchedule();
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return taskExecuteContext.await(time, timeUnit);
	}

	public boolean cancel() {
		return this.taskExecuteContext.cancel();
	}

	public String destroy() throws InterruptedException {
		return this.taskExecuteContext.destroy();
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.taskExecuteContext.execute(startTaskName);
	}

	public String getDescription() {
		String result = this.taskExecuteContext.getTaskExecuteStrategy()
				.getDescription();
		return result;
	}

	public boolean isEndTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isEndTask();
	}

	public Scheduler getScheduler() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getScheduler();
	}

	public boolean isShutdownTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.isShutdownTask();
	}

	public boolean isStartTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isStartTask();
	}

	public Object getTask() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTask();
	}

	public Class getTaskClass() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskClass();
	}

	public long getTaskId() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskId();
	}

	public String getTaskName() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getTaskName();
	}

	public TaskThreadPool getThreadPool() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getThreadPool();
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

	public String initialize() throws InterruptedException {
		return this.taskExecuteContext.initialize();
	}

	public boolean isExecute() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isExecute();
	}

	public void load() {
		this.taskExecuteContext.getTaskExecuteStrategy().load();
	}

	public void prepare() {
		this.taskExecuteContext.prepare();
	}

	public void save() {
		this.taskExecuteContext.getTaskExecuteStrategy().save();
	}

	public void setEndTask(boolean endTask) {
		this.taskExecuteContext.getTaskExecuteStrategy().setEndTask(endTask);
	}

	public void setExecute(boolean executed) {
		this.taskExecuteContext.getTaskExecuteStrategy().setExecute(executed);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteContext.setGetterSignal(getterSignal);
	}

	public void setScheduler(Scheduler scheduler) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setScheduler(scheduler);
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.taskExecuteContext.getTaskExecuteStrategy().setShutdownTask(
				shutdownTask);
	}

	public void setStartTask(boolean startTask) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setStartTask(startTask);
	}

	public void setTask(Object task) {
		this.taskExecuteContext.getTaskExecuteStrategy().setTask(task);
	}

	public void setTaskClass(Class taskClass) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setTaskClass(taskClass);
	}

	public void setTaskId(long taskId) {
		this.taskExecuteContext.getTaskExecuteStrategy().setTaskId(taskId);
	}

	public void setThreadPool(TaskThreadPool taskThreadPool) {
		this.taskExecuteContext.getTaskExecuteStrategy().setThreadPool(
				taskThreadPool);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(TaskTrigger taskTrigger) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setTrigger(taskTrigger);
	}

	public void waitOne() throws InterruptedException {
		this.taskExecuteContext.waitOne();
	}

}

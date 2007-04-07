package org.seasar.chronos.task.strategy.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.Scheduler;
import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.ThreadPoolType;
import org.seasar.chronos.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStrategyDecocator implements TaskExecuteStrategy {

	private final TaskExecuteStrategy taskExecuteStrategy;

	public TaskExecuteStrategyDecocator(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	public boolean checkMoveAnotherTask(String nextTaskName) {
		return this.taskExecuteStrategy.checkMoveAnotherTask(nextTaskName);
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.taskExecuteStrategy.await(time, timeUnit);
	}

	public boolean cancel() {
		return this.taskExecuteStrategy.cancel();
	}

	public String destroy() throws InterruptedException {
		return this.taskExecuteStrategy.destroy();
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.taskExecuteStrategy.execute(startTaskName);
	}

	public String initialize() throws InterruptedException {
		return this.taskExecuteStrategy.initialize();
	}

	public void prepare() {
		this.taskExecuteStrategy.prepare();
	}

	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteStrategy.setGetterSignal(getterSignal);
	}

	public void waitOne() throws InterruptedException {
		this.taskExecuteStrategy.waitOne();
	}

	public boolean getEndTask() {
		return this.taskExecuteStrategy.getEndTask();
	}

	public Scheduler getScheduler() {
		return this.taskExecuteStrategy.getScheduler();
	}

	public boolean getShutdownTask() {
		return this.taskExecuteStrategy.getShutdownTask();
	}

	public boolean getStartTask() {
		return this.taskExecuteStrategy.getStartTask();
	}

	public Object getTask() {
		return this.taskExecuteStrategy.getTask();
	}

	public Class getTaskClass() {
		return this.taskExecuteStrategy.getTaskClass();
	}

	public int getTaskId() {
		return this.taskExecuteStrategy.getTaskId();
	}

	public String getTaskName() {
		return this.taskExecuteStrategy.getTaskName();
	}

	public TaskThreadPool getThreadPool() {
		return this.taskExecuteStrategy.getThreadPool();
	}

	public int getThreadPoolSize() {
		return this.taskExecuteStrategy.getThreadPoolSize();
	}

	public ThreadPoolType getThreadPoolType() {
		return this.taskExecuteStrategy.getThreadPoolType();
	}

	public TaskTrigger getTrigger() {
		return this.taskExecuteStrategy.getTrigger();
	}

	public boolean isExecute() {
		return this.taskExecuteStrategy.isExecute();
	}

	public void setEndTask(boolean endTask) {
		this.taskExecuteStrategy.setEndTask(endTask);
	}

	public void setExecute(boolean executed) {
		this.taskExecuteStrategy.setExecute(executed);
	}

	public void setScheduler(Scheduler scheduler) {
		this.taskExecuteStrategy.setScheduler(scheduler);
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.taskExecuteStrategy.setShutdownTask(shutdownTask);
	}

	public void setStartTask(boolean startTask) {
		this.taskExecuteStrategy.setStartTask(startTask);
	}

	public void setTask(Object task) {
		this.taskExecuteStrategy.setTask(task);
	}

	public void setTaskClass(Class taskClass) {
		this.taskExecuteStrategy.setTaskClass(taskClass);
	}

	public void setTaskId(int taskId) {
		this.taskExecuteStrategy.setTaskId(taskId);
	}

	public void setThreadPool(TaskThreadPool taskThreadPool) {
		this.taskExecuteStrategy.setThreadPool(taskThreadPool);
	}

	public void setTrigger(TaskTrigger taskTrigger) {
		this.taskExecuteStrategy.setTrigger(taskTrigger);
	}

	public void load() {
		this.taskExecuteStrategy.load();
	}

	public void save() {
		this.taskExecuteStrategy.save();
	}

}

package org.seasar.chronos.core.task.strategy;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecuteStrategyWrapper implements TaskExecuteStrategy {

	private final TaskExecuteStrategy taskExecuteStrategy;

	public TaskExecuteStrategyWrapper(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	public boolean isReSchedule() {
		return this.taskExecuteStrategy.isReSchedule();
	}

	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.taskExecuteStrategy.await(time, timeUnit);
	}

	public boolean cancel() {
		return this.taskExecuteStrategy.cancel();
	}

	public boolean checkMoveAnotherTask(String nextTaskName) {
		return this.taskExecuteStrategy.checkMoveAnotherTask(nextTaskName);
	}

	public String destroy() throws InterruptedException {
		return this.taskExecuteStrategy.destroy();
	}

	public void execute(String startTaskName) throws InterruptedException {
		this.taskExecuteStrategy.execute(startTaskName);
	}

	public String getDescription() {
		return this.taskExecuteStrategy.getDescription();
	}

	public boolean isEndTask() {
		return this.taskExecuteStrategy.isEndTask();
	}

	public Scheduler getScheduler() {
		return this.taskExecuteStrategy.getScheduler();
	}

	public boolean isShutdownTask() {
		return this.taskExecuteStrategy.isShutdownTask();
	}

	public boolean isStartTask() {
		return this.taskExecuteStrategy.isStartTask();
	}

	public Object getTask() {
		return this.taskExecuteStrategy.getTask();
	}

	public Class<?> getTaskClass() {
		return this.taskExecuteStrategy.getTaskClass();
	}

	public long getTaskId() {
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

	public String initialize() throws InterruptedException {
		return this.taskExecuteStrategy.initialize();
	}

	public boolean isExecuted() {
		return this.taskExecuteStrategy.isExecuted();
	}

	public void load() {
		this.taskExecuteStrategy.load();
	}

	public void save() {
		this.taskExecuteStrategy.save();
	}

	public void setEndTask(boolean endTask) {
		this.taskExecuteStrategy.setEndTask(endTask);
	}

	public void setExecuted(boolean executed) {
		this.taskExecuteStrategy.setExecuted(executed);
	}

	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteStrategy.setGetterSignal(getterSignal);
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

	public void setTaskClass(Class<?> taskClass) {
		this.taskExecuteStrategy.setTaskClass(taskClass);
	}

	public void setTaskId(long taskId) {
		this.taskExecuteStrategy.setTaskId(taskId);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setThreadPool(TaskThreadPool taskThreadPool) {
		this.taskExecuteStrategy.setThreadPool(taskThreadPool);
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(TaskTrigger taskTrigger) {
		this.taskExecuteStrategy.setTrigger(taskTrigger);
	}

	public void waitOne() throws InterruptedException {
		this.taskExecuteStrategy.waitOne();
	}

	public void setComponentDef(ComponentDef componentDef) {
		this.taskExecuteStrategy.setComponentDef(componentDef);
	}

	public void prepare() {
		this.taskExecuteStrategy.prepare();
	}

	public void unprepare() {
		this.taskExecuteStrategy.unprepare();
	}

	public TaskPropertyReader getTaskPropertyReader() {
		return this.taskExecuteStrategy.getTaskPropertyReader();
	}

	public TaskPropertyWriter getTaskPropertyWriter() {
		return this.taskExecuteStrategy.getTaskPropertyWriter();
	}

	public void hotdeployStart() {
		this.taskExecuteStrategy.hotdeployStart();
	}

	public void hotdeployStop() {
		this.taskExecuteStrategy.hotdeployStop();
	}

	public boolean isPrepared() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}

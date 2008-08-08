/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.task.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskExecutorServiceImpl implements TaskExecutorService {

	private static final long serialVersionUID = 1L;

	private final TaskExecuteContext taskExecuteContext;

	public TaskExecutorServiceImpl(TaskExecuteContext taskExecuteContext) {
		this.taskExecuteContext = taskExecuteContext;
	}

	public boolean isReScheduleTask() {
		return taskExecuteContext.getTaskExecuteStrategy().isReScheduleTask();
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

	public Class<?> getTaskClass() {
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

	public boolean isExecuted() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isExecuted();
	}

	public void setEndTask(boolean endTask) {
		this.taskExecuteContext.getTaskExecuteStrategy().setEndTask(endTask);
	}

	public void setExecuted(boolean executed) {
		this.taskExecuteContext.getTaskExecuteStrategy().setExecuted(executed);
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

	public void setTaskClass(Class<?> taskClass) {
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

	public void setComponentDef(ComponentDef componentDef) {
		this.taskExecuteContext.getTaskExecuteStrategy().setComponentDef(
				componentDef);
	}

	public boolean isPrepared() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isPrepared();
	}

	public void prepare() {
		this.taskExecuteContext.getTaskExecuteStrategy().prepare();
	}

	public void unprepare() {
		this.taskExecuteContext.getTaskExecuteStrategy().unprepare();
	}

	public TaskPropertyReader getTaskPropertyReader() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.getTaskPropertyReader();
	}

	public TaskPropertyWriter getTaskPropertyWriter() {
		return this.taskExecuteContext.getTaskExecuteStrategy()
				.getTaskPropertyWriter();
	}

	public void hotdeployStart() {
		this.taskExecuteContext.getTaskExecuteStrategy().hotdeployStart();
	}

	public void hotdeployStop() {
		this.taskExecuteContext.getTaskExecuteStrategy().hotdeployStop();
	}

	public Exception getException() {
		return this.taskExecuteContext.getTaskExecuteStrategy().getException();
	}

	public void setException(Exception exception) {
		this.taskExecuteContext.getTaskExecuteStrategy()
				.setException(exception);
	}

	public boolean isHotdeployDisabled() {
		return this.taskExecuteContext.getTaskExecuteStrategy().isHotdeployDisabled();
	}

	public void setHotdeployDisabled(boolean hotdeployDisabled) {
		this.taskExecuteContext.getTaskExecuteStrategy().setHotdeployDisabled(hotdeployDisabled);
	}
}

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
package org.seasar.chronos.core.task.state.impl;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.TaskExecuteContext;
import org.seasar.chronos.core.task.state.TaskExecuteState;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public abstract class AbstractTaskExecuteContext implements TaskExecuteContext {

	private TaskExecuteState currentState;

	// private Object task;

	// private Class taskClass;

	// private Object getterSignal;

	private final TaskExecuteStrategy taskExecuteStrategy;

	private final TaskExecuteState taskExecuteStateInitialized;

	private final TaskExecuteState taskExecuteStateNonInitialized;

	public AbstractTaskExecuteContext(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
		this.taskExecuteStateInitialized = new TaskExecuteStateInitialized(
				taskExecuteStrategy);
		this.taskExecuteStateNonInitialized = new TaskExecuteStateNonInitialized(
				taskExecuteStrategy);
		this.currentState = taskExecuteStateNonInitialized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#await(long,
	 *      java.util.concurrent.TimeUnit)
	 */
	public boolean await(long time, TimeUnit timeUnit)
			throws InterruptedException {
		return this.currentState.await(this, time, timeUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#cancel()
	 */
	public boolean cancel() {
		return this.currentState.cancel(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#changeState(org.seasar.chronos.core.task.state.TaskExecuteState)
	 */
	public void changeState(TaskExecuteState nextState) {
		this.currentState = nextState;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#destroy()
	 */
	public String destroy() throws InterruptedException {
		return this.currentState.destroy(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#execute(java.lang.String)
	 */
	public void execute(String startTaskName) throws InterruptedException {
		this.currentState.execute(this, startTaskName);
	}

	public Object getTask() {
		return taskExecuteStrategy.getTask();
	}

	public Class<?> getTaskClass() {
		return taskExecuteStrategy.getTaskClass();
	}

	public final TaskExecuteState getTaskExecuteStateInitialized() {
		return taskExecuteStateInitialized;
	}

	public final TaskExecuteState getTaskExecuteStateNonInitialize() {
		return taskExecuteStateNonInitialized;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#getTaskExecuteStrategy()
	 */
	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return this.currentState.getTaskExecuteStrategy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#initialize()
	 */
	public String initialize() throws InterruptedException {
		return this.currentState.initialize(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#setGetterSignal(java.lang.Object)
	 */
	@Binding(bindingType = BindingType.NONE)
	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteStrategy.setGetterSignal(getterSignal);
	}

	public void setTask(Object task) {
		this.taskExecuteStrategy.setTask(task);
	}

	public void setTaskClass(Class<?> taskClass) {
		this.taskExecuteStrategy.setTaskClass(taskClass);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.state.impl.TaskExecuteContext#waitOne()
	 */
	public void waitOne() throws InterruptedException {
		this.currentState.waitOne();
	}

}

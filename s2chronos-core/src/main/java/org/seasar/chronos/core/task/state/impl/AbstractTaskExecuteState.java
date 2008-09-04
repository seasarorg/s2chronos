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
import org.seasar.framework.log.Logger;

public abstract class AbstractTaskExecuteState implements TaskExecuteState {

	protected static final Logger log = Logger
			.getLogger(AbstractTaskExecuteState.class);

	private final TaskExecuteStrategy taskExecuteStrategy;

	public AbstractTaskExecuteState(TaskExecuteStrategy taskExecuteStrategy) {
		this.taskExecuteStrategy = taskExecuteStrategy;
	}

	public TaskExecuteStrategy getTaskExecuteStrategy() {
		return taskExecuteStrategy;
	}

	protected synchronized void changeState(TaskExecuteContext context,
			TaskExecuteState nextState) {
		context.changeState(nextState);
	}

	public void setGetterSignal(Object getterSignal) {
		this.taskExecuteStrategy.setGetterSignal(getterSignal);
	}

	public abstract void waitOne() throws InterruptedException;

	public abstract boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public abstract void execute(TaskExecuteContext context,
			String startTaskName) throws InterruptedException;

	public abstract boolean cancel(TaskExecuteContext context);

	public abstract String finish(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public abstract String start(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public abstract void initialize(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public abstract void destroy(AbstractTaskExecuteContext context)
			throws InterruptedException;
}

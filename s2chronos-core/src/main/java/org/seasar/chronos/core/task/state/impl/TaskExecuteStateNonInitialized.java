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
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public class TaskExecuteStateNonInitialized extends AbstractTaskExecuteState {

	private static final long serialVersionUID = -8692566736124238318L;

	public TaskExecuteStateNonInitialized(
			TaskExecuteStrategy taskExecuteStrategy) {
		super(taskExecuteStrategy);
	}

	@Override
	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException {
		return true;
	}

	@Override
	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException {

	}

	@Override
	public boolean cancel(TaskExecuteContext context) {
		return false;
	}

	@Override
	public String destroy(AbstractTaskExecuteContext context) {
		return null;
	}

	@Override
	public String initialize(AbstractTaskExecuteContext context)
			throws InterruptedException {
		String result = this.getTaskExecuteStrategy().initialize();
		this.changeState(context, context.getTaskExecuteStateInitialized());
		return result;
	}

	@Override
	public void waitOne() {

	}

}

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
package org.seasar.chronos.core.task.state;

import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.task.state.impl.AbstractTaskExecuteContext;
import org.seasar.chronos.core.task.strategy.TaskExecuteStrategy;

public interface TaskExecuteState {

	public TaskExecuteStrategy getTaskExecuteStrategy();

	public void initialize(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public String start(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public void execute(TaskExecuteContext context, String startTaskName)
			throws InterruptedException;

	public boolean cancel(TaskExecuteContext context);

	public boolean await(TaskExecuteContext context, long time,
			TimeUnit timeUnit) throws InterruptedException;

	public String finish(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public void destroy(AbstractTaskExecuteContext context)
			throws InterruptedException;

	public void waitOne() throws InterruptedException;

}

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
package org.seasar.chronos.core.handler.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.seasar.chronos.core.event.SchedulerEventHandler;
import org.seasar.chronos.core.handler.ScheduleExecuteHandler;
import org.seasar.chronos.core.model.schedule.TaskScheduleEntryManager;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;
import org.seasar.framework.log.Logger;

public abstract class AbstractScheduleExecuteHandler implements
		ScheduleExecuteHandler {

	protected static final Logger log = Logger
			.getLogger(AbstractScheduleExecuteHandler.class);

	protected TaskScheduleEntryManager taskScheduleEntryManager = TaskScheduleEntryManager
			.getInstance();

	protected ExecutorService executorService;

	protected SchedulerEventHandler schedulerEventHandler;

	protected AtomicBoolean pause;

	protected AtomicBoolean paused;

	public abstract void handleRequest() throws InterruptedException;

	@Binding(bindingType = BindingType.NONE)
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	public void setPause(AtomicBoolean pause) {
		this.pause = pause;
	}

	public void setPaused(AtomicBoolean paused) {
		this.paused = paused;
	}

	public void setSchedulerEventHandler(
			SchedulerEventHandler schedulerEventHandler) {
		this.schedulerEventHandler = schedulerEventHandler;
	}

}

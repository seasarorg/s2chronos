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
package org.seasar.chronos.core;

import org.seasar.chronos.core.impl.AbstractScheduler;

/**
 * 
 * @author j5ik2o
 * 
 */
public abstract class SchedulerWrapper extends AbstractScheduler {

	private final Scheduler scheduler;

	public SchedulerWrapper(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public boolean addListener(SchedulerEventListener listener) {
		return scheduler.addListener(listener);
	}

	// public boolean addTask(String taskName) {
	// return scheduler.addTask(taskName);
	// }

	public boolean addTask(Class<?> taskClass) {
		return scheduler.addTask(taskClass);
	}

	public boolean removeTask(Class<?> taskClass) {
		return scheduler.removeTask(taskClass);
	}

	public SchedulerConfiguration getSchedulerConfiguration() {
		return scheduler.getSchedulerConfiguration();
	}

	public boolean isPaused() {
		return scheduler.isPaused();
	}

	public void join() {
		scheduler.join();
	}

	public void pause() {
		scheduler.pause();
	}

	public boolean removeListener(SchedulerEventListener listener) {
		return scheduler.removeListener(listener);
	}

	public void setSchedulerConfiguration(
			SchedulerConfiguration schedulerConfiguration) {
		scheduler.setSchedulerConfiguration(schedulerConfiguration);
	}

	public void shutdown() {
		this.scheduler.shutdown();
	}

	public void start() {
		this.scheduler.start();
	}

	public void process() {
		this.scheduler.process();
	}
}

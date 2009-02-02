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
package org.seasar.chronos.core.model.schedule;

import java.util.Date;
import java.util.concurrent.Future;

import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public class ScheduleEntryWrapper extends AbstractScheduleEntry {

	private final TaskScheduleEntry taskScheduleEntry;

	public ScheduleEntryWrapper(TaskScheduleEntry taskScheduleEntry) {
		this.taskScheduleEntry = taskScheduleEntry;
	}

	public ComponentDef getComponentDef() {
		return taskScheduleEntry.getComponentDef();
	}

	@Override
	public Long getScheduleId() {
		return taskScheduleEntry.getScheduleId();
	}

	public Object getTask() {
		return taskScheduleEntry.getTask();
	}

	public Class<?> getTaskClass() {
		return taskScheduleEntry.getTaskClass();
	}

	public TaskExecutorService getTaskExecutorService() {
		return taskScheduleEntry.getTaskExecutorService();
	}

	public Future<TaskExecutorService> getTaskStaterFuture() {
		return taskScheduleEntry.getTaskStaterFuture();
	}

	public TaskStateType getTaskStateType() {
		return taskScheduleEntry.getTaskStateType();
	}

	public void setComponentDef(ComponentDef componentDef) {
		taskScheduleEntry.setComponentDef(componentDef);
	}

	@Override
	public void setScheduleId(Long scheduleId) {
		taskScheduleEntry.setScheduleId(scheduleId);
	}

	public void setTask(Object target) {
		taskScheduleEntry.setTask(target);
	}

	public void setTaskClass(Class<?> targetClass) {
		taskScheduleEntry.setTaskClass(targetClass);
	}

	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		taskScheduleEntry.setTaskExecutorService(taskExecutorService);
	}

	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		taskScheduleEntry.setTaskStaterFuture(future);
	}

	public void setTaskStateType(TaskStateType taskStateType) {
		taskScheduleEntry.setTaskStateType(taskStateType);
	}

	public Date getUnScheduledDate() {
		return taskScheduleEntry.getUnScheduledDate();
	}

	public void setUnScheduledDate(Date date) {
		taskScheduleEntry.setUnScheduledDate(date);
	}

}

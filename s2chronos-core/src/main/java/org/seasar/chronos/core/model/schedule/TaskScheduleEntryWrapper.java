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

/**
 * {@link TaskScheduleEntry}のラッパークラスです．
 * 
 * @author j5i2ko
 */
@SuppressWarnings("serial")
public class TaskScheduleEntryWrapper extends AbstractTaskScheduleEntry {
	private final TaskScheduleEntry taskScheduleEntry;

	/**
	 * コンストラクタです．
	 * 
	 * @param taskScheduleEntry
	 */
	public TaskScheduleEntryWrapper(TaskScheduleEntry taskScheduleEntry) {
		this.taskScheduleEntry = taskScheduleEntry;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getComponentDef()
	 */
	public ComponentDef getComponentDef() {
		return taskScheduleEntry.getComponentDef();
	}

	/*
	 * (非 Javadoc)
	 * @seeorg.seasar.chronos.core.model.schedule.AbstractTaskScheduleEntry#
	 * getScheduleId()
	 */
	@Override
	public Long getScheduleId() {
		return taskScheduleEntry.getScheduleId();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTask()
	 */
	public Object getTask() {
		return taskScheduleEntry.getTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTaskClass()
	 */
	public Class<?> getTaskClass() {
		return taskScheduleEntry.getTaskClass();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#getTaskExecutorService()
	 */
	public TaskExecutorService getTaskExecutorService() {
		return taskScheduleEntry.getTaskExecutorService();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#getTaskStaterFuture()
	 */
	public Future<TaskExecutorService> getTaskStaterFuture() {
		return taskScheduleEntry.getTaskStaterFuture();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTaskStateType()
	 */
	public TaskStateType getTaskStateType() {
		return taskScheduleEntry.getTaskStateType();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setComponentDef(org.seasar
	 * .framework.container.ComponentDef)
	 */
	public void setComponentDef(ComponentDef componentDef) {
		taskScheduleEntry.setComponentDef(componentDef);
	}

	/*
	 * (非 Javadoc)
	 * @seeorg.seasar.chronos.core.model.schedule.AbstractTaskScheduleEntry#
	 * setScheduleId(java.lang.Long)
	 */
	@Override
	public void setScheduleId(Long scheduleId) {
		taskScheduleEntry.setScheduleId(scheduleId);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTask(java.lang.Object)
	 */
	public void setTask(Object target) {
		taskScheduleEntry.setTask(target);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskClass(java.lang
	 * .Class)
	 */
	public void setTaskClass(Class<?> targetClass) {
		taskScheduleEntry.setTaskClass(targetClass);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskExecutorService
	 * (org.seasar.chronos.core.task.TaskExecutorService)
	 */
	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		taskScheduleEntry.setTaskExecutorService(taskExecutorService);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskStaterFuture(java
	 * .util.concurrent.Future)
	 */
	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		taskScheduleEntry.setTaskStaterFuture(future);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskStateType(org.
	 * seasar.chronos.core.model.TaskStateType)
	 */
	public void setTaskStateType(TaskStateType taskStateType) {
		taskScheduleEntry.setTaskStateType(taskStateType);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getUnScheduledDate()
	 */
	public Date getUnScheduledDate() {
		return taskScheduleEntry.getUnScheduledDate();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setUnScheduledDate(java
	 * .util.Date)
	 */
	public void setUnScheduledDate(Date date) {
		taskScheduleEntry.setUnScheduledDate(date);
	}
}

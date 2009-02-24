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

import org.seasar.chronos.core.model.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

/**
 * タスクスケジュールエントリの実装クラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class TaskScheduleEntryImpl extends AbstractTaskScheduleEntry {
	private transient ComponentDef componentDef;

	private Object task;

	private Class<?> taskClass;

	private TaskStateType taskStateType;

	private transient TaskExecutorService taskExecutorService;

	private transient Future<TaskExecutorService> future;

	private Date unScheduledDate;

	/**
	 * コンストラクタです．
	 */
	public TaskScheduleEntryImpl() {
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getComponentDef()
	 */
	public ComponentDef getComponentDef() {
		return componentDef;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTask()
	 */
	public Object getTask() {
		return task;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTaskClass()
	 */
	public Class<?> getTaskClass() {
		return taskClass;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#getTaskExecutorService()
	 */
	public TaskExecutorService getTaskExecutorService() {
		return taskExecutorService;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#getTaskStaterFuture()
	 */
	public Future<TaskExecutorService> getTaskStaterFuture() {
		return future;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getTaskStateType()
	 */
	public TaskStateType getTaskStateType() {
		return taskStateType;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskScheduleEntry#getUnScheduledDate()
	 */
	public Date getUnScheduledDate() {
		return (Date) unScheduledDate.clone();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setComponentDef(org.seasar
	 * .framework.container.ComponentDef)
	 */
	public void setComponentDef(ComponentDef componentDef) {
		this.componentDef = componentDef;
		// this.setTask(componentDef.getComponent());
		this.setTaskClass(componentDef.getComponentClass());
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTask(java.lang.Object)
	 */
	public void setTask(Object target) {
		this.task = target;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskClass(java.lang
	 * .Class)
	 */
	public void setTaskClass(Class<?> targetClass) {
		this.taskClass = targetClass;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskExecutorService
	 * (org.seasar.chronos.core.task.TaskExecutorService)
	 */
	public void setTaskExecutorService(TaskExecutorService taskExecutorService) {
		this.taskExecutorService = taskExecutorService;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskStaterFuture(java
	 * .util.concurrent.Future)
	 */
	public void setTaskStaterFuture(Future<TaskExecutorService> future) {
		this.future = future;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setTaskStateType(org.
	 * seasar.chronos.core.model.TaskStateType)
	 */
	public void setTaskStateType(TaskStateType taskStateType) {
		this.taskStateType = taskStateType;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskScheduleEntry#setUnScheduledDate(java
	 * .util.Date)
	 */
	public void setUnScheduledDate(Date date) {
		unScheduledDate = (Date) date.clone();
	}
}

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

import org.seasar.chronos.core.model.TaskThreadPool;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.model.ThreadPoolType;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class TaskPropertyReaderImpl implements TaskPropertyReader {

	private BeanDesc beanDesc;

	private Object task;

	public BeanDesc getBeanDesc() {
		return this.beanDesc;
	}

	public String getDescription(String defaultValue) {
		if (this.hasDescription()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
			return (String) pd.getValue(this.task);
		}
		return defaultValue;
	}

	public Exception getException(Exception defaultValue) {
		Exception result = defaultValue;
		if (hasException()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			result = (Exception) pd.getValue(this.task);
		}
		return result;
	}

	public Object getTask() {
		return this.task;
	}

	public Class<?> getTaskClass() {
		return this.beanDesc.getBeanClass();
	}

	public Long getTaskId(Long defaultValue) {
		if (this.hasTaskId()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKID);
			return (Long) pd.getValue(this.task);
		}
		return defaultValue;
	}

	public String getTaskName(String defaultValue) {
		if (this.hasTaskName()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
			return (String) pd.getValue(this.task);
		}
		return defaultValue;
	}

	public TaskThreadPool getThreadPool(TaskThreadPool defaultValue) {
		if (this.hasThreadPool()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREADPOOL);
			TaskThreadPool result = (TaskThreadPool) pd.getValue(this.task);
			if (result != null) {
				return result;
			}
		}
		return defaultValue;
	}

	public int getThreadPoolSize(int defaultValue) {
		if (this.hasThreadPoolSize()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
			Integer result = (Integer) pd.getValue(this.task);
			if (result != null) {
				return result;
			}
		}
		return defaultValue;
	}

	public ThreadPoolType getThreadPoolType(ThreadPoolType defaultType) {
		ThreadPoolType result = defaultType;
		if (this.hasThreadPoolType()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_TYPE);
			result = (ThreadPoolType) pd.getValue(this.task);
		}
		return result;
	}

	public TaskTrigger getTrigger(TaskTrigger defaultValue) {
		TaskTrigger result = defaultValue;
		if (this.hasTrigger()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TRIGGER);
			result = (TaskTrigger) pd.getValue(this.task);
		}
		return result;
	}

	public boolean hasDescription() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasEndTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_END_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_END_TASK);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasException() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_EXCEPTION);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXCEPTION);
			result = pd.isReadable();
			return result;
		}
		return false;
	}

	public boolean hasExecuting() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasExecuted() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTED);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTED);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasReScheduleTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_RE_SCHEDULE_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RE_SCHEDULE_TASK);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasShutdownTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_SHUTDOWN_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasStartTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_START_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_START_TASK);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasTaskId() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_TASKID);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKID);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasTaskName() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasThreadPool() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_THREADPOOL);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREADPOOL);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasThreadPoolSize() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasThreadPoolType() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasTrigger() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_TRIGGER);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TRIGGER);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean hasForceUnScheduleTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_FORCE_UN_SCHEDULE_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_FORCE_UN_SCHEDULE_TASK);
			result = pd.isReadable();
			return result;
		}
		return result;
	}

	public boolean isEndTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasEndTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_END_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isExecuting(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasExecuting()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isExecuted(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasExecuted()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTED);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isReScheduleTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasReScheduleTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RE_SCHEDULE_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isShutdownTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasShutdownTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isForceUnScheduleTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasForceUnScheduleTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_FORCE_UN_SCHEDULE_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isStartTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasStartTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_START_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public void setup(Object task, BeanDesc beanDesc) {
		this.task = task;
		this.beanDesc = beanDesc;
	}

	public void setup(Object task, Class<?> taskClass) {
		this.task = task;
		this.beanDesc = BeanDescFactory.getBeanDesc(taskClass);
	}

}

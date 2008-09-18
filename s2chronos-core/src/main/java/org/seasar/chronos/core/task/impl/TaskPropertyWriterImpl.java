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

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskPropertyWriterImpl implements TaskPropertyWriter {

	private TaskPropertyReader taskPropertyReader;

	public Object getTask() {
		return this.task;
	}

	public Class<?> getTaskClass() {
		return this.beanDesc.getBeanClass();
	}

	public BeanDesc getBeanDesc() {
		return this.beanDesc;
	}

	private BeanDesc beanDesc;
	private Object task;

	public void setup(Object task, Class<?> taskClass) {
		this.task = task;
		this.beanDesc = BeanDescFactory.getBeanDesc(taskClass);
		taskPropertyReader.setup(task, beanDesc);
	}

	public void setup(Object task, BeanDesc beanDesc) {
		this.task = task;
		this.beanDesc = beanDesc;
		taskPropertyReader.setup(task, beanDesc);
	}

	public void setThreadPoolType(ThreadPoolType value) {
		if (this.hasThreadPoolType()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_TYPE);
			pd.setValue(this.task, value);
		}
	}

	public void setDescription(String value) {
		if (this.hasDescription()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
			pd.setValue(this.task, value);
		}
	}

	public void setEndTask(boolean value) {
		if (this.hasEndTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_END_TASK);
			pd.setValue(this.task, value);
		}
	}

	public void setExecuting(boolean value) {
		if (this.hasExecuting()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
			pd.setValue(this.task, value);
		}
	}

	public void setExecuted(boolean value) {
		if (this.hasExecuted()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTED);
			pd.setValue(this.task, value);
		}
	}

	public void setReSchedule(boolean value) {
		if (this.hasReSchedule()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE_TASK);
			pd.setValue(this.task, value);
		}
	}

	public void setShutdownTask(boolean value) {
		if (this.hasShutdownTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			pd.setValue(this.task, value);
		}
	}

	public void setStartTask(boolean value) {
		if (this.hasStartTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_START_TASK);
			pd.setValue(this.task, value);
		}
	}

	public void setTaskId(Long value) {
		if (this.hasTaskId()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKID);
			pd.setValue(this.task, value);
		}
	}

	public void setTaskName(String value) {
		if (this.hasTaskName()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TASKNAME);
			pd.setValue(this.task, value);
		}
	}

	@Binding(bindingType = BindingType.NONE)
	public void setThreadPool(TaskThreadPool value) {
		if (this.hasThreadPool()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREADPOOL);
			pd.setValue(this.task, value);
		}
	}

	public void setThreadPoolSize(int value) {
		if (this.hasThreadPoolSize()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
			pd.setValue(this.task, value);
		}
	}

	@Binding(bindingType = BindingType.NONE)
	public void setTrigger(TaskTrigger value) {
		if (this.beanDesc.hasPropertyDesc(TaskConstant.PROPERTY_NAME_TRIGGER)) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_TRIGGER);
			pd.setValue(this.task, value);
		}
	}

	public boolean hasDescription() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_DESCRIPTION);
			result = pd.isWritable();
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
			result = pd.isWritable();
			return result;
		}
		return result;
	}

	public boolean hasExecuting() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXECUTING);
			result = pd.isWritable();
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
			result = pd.isWritable();
			return result;
		}
		return result;
	}

	public boolean hasReSchedule() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE_TASK);
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
			return result;
		}
		return result;
	}

	public boolean hasThreadPoolType() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_TYPE);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_THREAD_POOL_TYPE);
			result = pd.isWritable();
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
			result = pd.isWritable();
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
			result = pd.isWritable();
			return result;
		}
		return result;
	}

	public void setException(Exception value) {
		if (this.hasException()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_EXCEPTION);
			pd.setValue(this.task, value);
		}
	}

	public TaskPropertyReader getTaskPropertyReader() {
		return taskPropertyReader;
	}

	public void setTaskPropertyReader(TaskPropertyReader taskPropertyReader) {
		this.taskPropertyReader = taskPropertyReader;
	}

}

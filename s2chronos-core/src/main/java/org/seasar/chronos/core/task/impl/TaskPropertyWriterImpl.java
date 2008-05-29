package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.annotation.tiger.Binding;
import org.seasar.framework.container.annotation.tiger.BindingType;

public class TaskPropertyWriterImpl implements TaskPropertyWriter {

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

	public void loadTask(Object task, Class<?> taskClass) {
		this.task = task;
		this.beanDesc = BeanDescFactory.getBeanDesc(taskClass);
	}

	public void loadTask(Object task, BeanDesc beanDesc) {
		this.task = task;
		this.beanDesc = beanDesc;
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
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE);
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
			return result;
		}
		return result;
	}

	public boolean hasReSchedule() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskConstant.PROPERTY_NAME_RESCHEDULE);
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
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
			result = pd.hasWriteMethod();
			return result;
		}
		return result;
	}

}

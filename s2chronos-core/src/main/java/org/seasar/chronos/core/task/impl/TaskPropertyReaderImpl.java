package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.TaskPropertyConstant;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class TaskPropertyReaderImpl implements TaskPropertyReader {

	private final BeanDesc beanDesc;
	private final Object task;

	public TaskPropertyReaderImpl(Object task, Class<?> taskClass) {
		this.task = task;
		this.beanDesc = BeanDescFactory.getBeanDesc(taskClass);
	}

	public String getDescription(String defaultValue) {
		if (this.hasDescription()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_DESCRIPTION);
			return (String) pd.getValue(this.task);
		}
		return null;
	}

	public Long getTaskId(Long defaultValue) {
		if (this.hasTaskId()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKID);
			return (Long) pd.getValue(this.task);
		}
		return defaultValue;
	}

	public String getTaskName(String defaultValue) {
		if (this.hasTaskName()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKNAME);
			return (String) pd.getValue(this.task);
		}
		return defaultValue;
	}

	public int getThreadPoolSize(int defaultValue) {
		if (this.hasThreadPoolSize()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
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
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_THREAD_POOL_TYPE);
			result = (ThreadPoolType) pd.getValue(this.task);
		}
		return result;
	}

	public TaskTrigger getTrigger(TaskTrigger defaultValue) {
		TaskTrigger result = defaultValue;
		if (this.hasTrigger()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TRIGGER);
			result = (TaskTrigger) pd.getValue(this.task);
		}
		return result;
	}

	public boolean isEndTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasEndTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_END_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isExecuted(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasExecuted()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_EXECUTED);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isReSchedule(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasReSchedule()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_RESCHEDULE);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isShutdownTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasShutdownTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean isStartTask(boolean defaultValue) {
		boolean result = defaultValue;
		if (this.hasStartTask()) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_START_TASK);
			result = Boolean.TRUE.equals(pd.getValue(this.task));
		}
		return result;
	}

	public boolean hasDescription() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_DESCRIPTION);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_DESCRIPTION);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasTaskId() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKID);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKID);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasTaskName() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKNAME);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKNAME);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasThreadPoolSize() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_THREAD_POOL_SIZE);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasThreadPoolType() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKNAME);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TASKNAME);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasTrigger() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TRIGGER);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_TRIGGER);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasEndTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_END_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_END_TASK);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasExecuted() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_EXECUTED);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_EXECUTED);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasReSchedule() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_RESCHEDULE);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_RESCHEDULE);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasShutdownTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_SHUTDOWN_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_SHUTDOWN_TASK);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}

	public boolean hasStartTask() {
		boolean result = this.beanDesc
				.hasPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_START_TASK);
		if (result) {
			PropertyDesc pd = this.beanDesc
					.getPropertyDesc(TaskPropertyConstant.PROPERTY_NAME_START_TASK);
			result = pd.hasReadMethod();
			return result;
		}
		return result;
	}
}

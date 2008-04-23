package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.TaskPropertyReadCommandFactory;
import org.seasar.chronos.core.task.command.TaskPropertyCommand;
import org.seasar.framework.container.S2Container;

public class TaskPropertyReadCommandFactoryImpl implements
		TaskPropertyReadCommandFactory {

	private static final String TASK_GET_TASK_NAME_PROPERTY_READ_COMMAND = "taskGetTaskNamePropertyReadCommand";
	private static final String TASK_GET_THREAD_POOL_TYPE_PROPERTY_READ_COMMAND = "taskGetThreadPoolTypePropertyReadCommand";
	private static final String TASK_GET_THREAD_POOL_SIZE_PROPERTY_READ_COMMAND = "taskGetThreadPoolSizePropertyReadCommand";
	private static final String TASK_IS_START_TASK_PROPERTY_READ_COMMAND = "taskIsStartTaskPropertyReadCommand";
	private static final String TASK_IS_END_TASK_PROPERTY_READ_COMMAND = "taskIsEndTaskPropertyReadCommand";
	private static final String TASK_IS_RE_SCHEDULE_PROPERTY_READ_COMMAND = "taskIsReSchedulePropertyReadCommand";

	private S2Container s2Container;

	public TaskPropertyCommand create(Method method) {
		TaskPropertyCommand result = null;
		if (method.getName().equals(TaskConstant.METHOD_NAME_IS_END_TASK)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_IS_END_TASK_PROPERTY_READ_COMMAND);
		} else if (method.getName().equals(
				TaskConstant.METHOD_NAME_IS_START_TASK)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_IS_START_TASK_PROPERTY_READ_COMMAND);
		} else if (method.getName().equals(
				TaskConstant.METHOD_NAME_GET_THREAD_POOL_SIZE)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_GET_THREAD_POOL_SIZE_PROPERTY_READ_COMMAND);
		} else if (method.getName().equals(
				TaskConstant.METHOD_NAME_GET_THREAD_POOL_TYPE)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_GET_THREAD_POOL_TYPE_PROPERTY_READ_COMMAND);
		} else if (method.getName().equals(
				TaskConstant.METHOD_NAME_IS_RE_SCHEDULE)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_IS_RE_SCHEDULE_PROPERTY_READ_COMMAND);
		} else if (method.getName().equals(
				TaskConstant.METHOD_NAME_GET_TASK_NAME)) {
			result = (TaskPropertyCommand) s2Container
					.getComponent(TASK_GET_TASK_NAME_PROPERTY_READ_COMMAND);
		}
		return result;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
	}
}

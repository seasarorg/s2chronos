package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.TaskPropertyReadCommandFactory;
import org.seasar.chronos.core.task.command.TaskPropertCommand;
import org.seasar.framework.container.S2Container;

public class TaskPropertyReadCommandFactoryImpl implements
		TaskPropertyReadCommandFactory {

	private S2Container s2Container;

	public TaskPropertCommand create(Method method) {
		TaskPropertCommand result = null;
		if (method.getName().equals("isEndTask")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskIsEndTaskPropertyReadCommand");
		} else if (method.getName().equals("isStartTask")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskIsStartTaskPropertyReadCommand");
		} else if (method.getName().equals("getThreadPoolSize")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskGetThreadPoolSizePropertyReadCommand");
		} else if (method.getName().equals("getThreadPoolType")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskGetThreadPoolTypePropertyReadCommand");
		} else if (method.getName().equals("isReSchedule")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskIsReSchedulePropertyReadCommand");
		}
		return result;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
	}
}

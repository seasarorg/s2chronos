package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.TaskPropertyWriteCommandFactory;
import org.seasar.chronos.core.task.command.TaskPropertCommand;
import org.seasar.framework.container.S2Container;

public class TaskPropertyWriteCommandFactoryImpl implements
		TaskPropertyWriteCommandFactory {

	private S2Container s2Container;

	public TaskPropertCommand create(Method method) {
		TaskPropertCommand result = null;
		if (method.getName().equals("setEndTask")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskSetEndTaskPropertyWriteCommand");
		} else if (method.getName().equals("setStartTask")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskSetStartTaskPropertyWriteCommand");
		} else if (method.getName().equals("setThreadPoolSize")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskSetThreadPoolSizePropertyWriteCommand");
		} else if (method.getName().equals("setThreadPoolType")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskSetThreadPoolTypePropertyWriteCommand");
		} else if (method.getName().equals("setReSchedule")) {
			result = (TaskPropertCommand) s2Container
					.getComponent("taskSetReSchedulePropertyWriteCommand");
		}
		return result;
	}

	public void setS2Container(S2Container container) {
		this.s2Container = container;
	}
}

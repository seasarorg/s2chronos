package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.TaskPropertyWriteCommandFactory;
import org.seasar.chronos.core.task.command.TaskPropertCommand;
import org.seasar.chronos.core.task.command.impl.TaskSetEndTaskPropertyWriteCommand;
import org.seasar.chronos.core.task.command.impl.TaskSetReSchedulePropertyWriteCommand;
import org.seasar.chronos.core.task.command.impl.TaskSetStartTaskPropertyWriteCommand;
import org.seasar.chronos.core.task.command.impl.TaskSetThreadPoolSizePropertyWriteCommand;
import org.seasar.chronos.core.task.command.impl.TaskSetThreadPoolTypePropertyWriteCommand;

public class TaskPropertyWriteCommandFactoryImpl implements
		TaskPropertyWriteCommandFactory {

	public TaskPropertCommand create(Method method) {
		TaskPropertCommand result = null;
		if (method.getName().equals("setEndTask")) {
			result = new TaskSetEndTaskPropertyWriteCommand();
		} else if (method.getName().equals("setStartTask")) {
			result = new TaskSetStartTaskPropertyWriteCommand();
		} else if (method.getName().equals("setThreadPoolSize")) {
			result = new TaskSetThreadPoolSizePropertyWriteCommand();
		} else if (method.getName().equals("setThreadPoolType")) {
			result = new TaskSetThreadPoolTypePropertyWriteCommand();
		} else if (method.getName().equals("setReSchedule")) {
			result = new TaskSetReSchedulePropertyWriteCommand();
		}
		return result;
	}

}

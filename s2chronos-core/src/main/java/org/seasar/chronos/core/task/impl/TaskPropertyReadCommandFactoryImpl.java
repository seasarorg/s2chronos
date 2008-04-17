package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.TaskPropertyReadCommandFactory;
import org.seasar.chronos.core.task.command.TaskPropertCommand;
import org.seasar.chronos.core.task.command.impl.TaskGetThreadPoolSizePropertyReadCommand;
import org.seasar.chronos.core.task.command.impl.TaskGetThreadPoolTypePropertyReadCommand;
import org.seasar.chronos.core.task.command.impl.TaskIsEndTaskPropertyReadCommand;
import org.seasar.chronos.core.task.command.impl.TaskIsReSchedulePropertyReadCommand;
import org.seasar.chronos.core.task.command.impl.TaskIsStartTaskPropertyReadCommand;

public class TaskPropertyReadCommandFactoryImpl implements
		TaskPropertyReadCommandFactory {

	public TaskPropertCommand create(Method method) {
		TaskPropertCommand result = null;
		if (method.getName().equals("isEndTask")) {
			result = new TaskIsEndTaskPropertyReadCommand();
		} else if (method.getName().equals("isStartTask")) {
			result = new TaskIsStartTaskPropertyReadCommand();
		} else if (method.getName().equals("getThreadPoolSize")) {
			result = new TaskGetThreadPoolSizePropertyReadCommand();
		} else if (method.getName().equals("getThreadPoolType")) {
			result = new TaskGetThreadPoolTypePropertyReadCommand();
		} else if (method.getName().equals("isReSchedule")) {
			result = new TaskIsReSchedulePropertyReadCommand();
		}
		return result;
	}
}

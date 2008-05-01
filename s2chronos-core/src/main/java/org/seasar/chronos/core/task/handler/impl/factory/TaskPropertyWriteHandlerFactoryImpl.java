package org.seasar.chronos.core.task.handler.impl.factory;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.AbstractTaskPropertyHandlerFactory;
import org.seasar.framework.container.S2Container;

public class TaskPropertyWriteHandlerFactoryImpl extends
		AbstractTaskPropertyHandlerFactory {

	// private static final String TASK_SET_RE_SCHEDULE_PROPERTY_WRITE_COMMAND =
	// "taskSetReSchedulePropertyWriteCommand";
	// private static final String
	// TASK_SET_THREAD_POOL_TYPE_PROPERTY_WRITE_COMMAND =
	// "taskSetThreadPoolTypePropertyWriteCommand";
	// private static final String
	// TASK_SET_THREAD_POOL_SIZE_PROPERTY_WRITE_COMMAND =
	// "taskSetThreadPoolSizePropertyWriteCommand";
	// private static final String TASK_SET_START_TASK_PROPERTY_WRITE_COMMAND =
	// "taskSetStartTaskPropertyWriteCommand";
	// private static final String TASK_SET_END_TASK_PROPERTY_WRITE_COMMAND =
	// "taskSetEndTaskPropertyWriteCommand";

	private S2Container s2Container;

	public TaskPropertyHandler create(Method method) {
		TaskPropertyHandler result = taskPropertyHandlerMap.get(method
				.getName());
		return result;
	}

	public void setS2Container(S2Container container) {
		this.s2Container = container;
	}
}

package org.seasar.chronos.core.task.handler.impl.factory;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.AbstractTaskPropertyHandlerFactory;

public class TaskPropertyReadHandlerFactoryImpl extends
		AbstractTaskPropertyHandlerFactory {

	public TaskPropertyHandler create(Method method) {
		if (method == null) {
			return null;
		}
		TaskPropertyHandler result = taskPropertyHandlerMap.get(method
				.getName());
		return result;
	}

}

package org.seasar.chronos.core.task.handler.factory;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;

public interface TaskPropertyHandlerFactory {

	public TaskPropertyHandler create(Method method);

}

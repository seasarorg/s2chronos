package org.seasar.chronos.core.task;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;

public interface TaskPropertyReadCommandFactory {

	public TaskPropertyHandler create(Method method);

}

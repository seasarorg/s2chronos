package org.seasar.chronos.core.task;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.command.TaskPropertyCommand;

public interface TaskPropertyReadCommandFactory {

	public TaskPropertyCommand create(Method method);

}

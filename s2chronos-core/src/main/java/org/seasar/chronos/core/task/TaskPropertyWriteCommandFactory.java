package org.seasar.chronos.core.task;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.command.TaskPropertyCommand;

public interface TaskPropertyWriteCommandFactory {

	public TaskPropertyCommand create(Method method);

}

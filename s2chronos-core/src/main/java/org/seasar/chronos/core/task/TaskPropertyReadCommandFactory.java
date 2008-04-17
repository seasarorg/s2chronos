package org.seasar.chronos.core.task;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.command.TaskPropertCommand;

public interface TaskPropertyReadCommandFactory {

	public TaskPropertCommand create(Method method);

}

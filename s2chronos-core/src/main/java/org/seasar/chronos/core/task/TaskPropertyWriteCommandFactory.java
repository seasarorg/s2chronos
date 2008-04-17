package org.seasar.chronos.core.task;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.command.TaskPropertCommand;

public interface TaskPropertyWriteCommandFactory {

	public TaskPropertCommand create(Method method);

}

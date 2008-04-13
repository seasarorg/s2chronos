package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.task.Task;

public interface TaskAnnotationReader {

	public boolean hasTask();

	public boolean hasTrigger();

	public Task getTask();

	public Class<?> getTrigger();

}

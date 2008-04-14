package org.seasar.chronos.core.task;

import org.seasar.chronos.core.annotation.task.Task;

public interface TaskAnnotationReader {

	public void loadTask(Class<?> taskClass);

	public boolean hasTaskAnnotation();

	public boolean hasTriggerAnnotation();

	public Task getTaskAnnotation();

	public Class<?> getTriggerAnnotationClass();

}

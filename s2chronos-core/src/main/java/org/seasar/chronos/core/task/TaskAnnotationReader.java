package org.seasar.chronos.core.task;

import java.lang.annotation.Annotation;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;

public interface TaskAnnotationReader {

	public interface TriggerAnnotationHandler {
		TaskTrigger process(Annotation annotaion,
				Class<?> triggerAnnotationClass);
	}

	public void loadTask(Class<?> taskClass);

	public boolean hasTaskAnnotation();

	public boolean hasTriggerAnnotation();

	public boolean hasTriggerProperty();

	public Task getTaskAnnotation();

	public TaskTrigger getTriggerAnnotationClass(
			TriggerAnnotationHandler triggerAnnotationHandler);

}

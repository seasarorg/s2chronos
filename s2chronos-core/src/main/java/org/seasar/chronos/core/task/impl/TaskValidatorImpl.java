package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskValidator;

public class TaskValidatorImpl implements TaskValidator {

	private TaskAnnotationReader taskAnnotationReader;

	private TaskPropertyReader taskPropertyReader;

	public boolean isValid(Object task, Class<?> taskClass) {
		taskPropertyReader.loadTask(task, taskClass);
		taskAnnotationReader.loadTask(taskClass);
		if ((taskAnnotationReader.hasTaskAnnotation() && taskAnnotationReader
				.hasTriggerAnnotation())
				|| (taskAnnotationReader.hasTaskAnnotation() && taskPropertyReader
						.hasTrigger())) {
			return true;
		}
		return false;
	}

}

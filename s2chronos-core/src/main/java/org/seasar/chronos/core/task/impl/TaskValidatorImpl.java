package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskValidator;

// TODO isValidはあとで見直す．
public class TaskValidatorImpl implements TaskValidator {

	private TaskAnnotationReader taskAnnotationReader;

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.TaskValidator#isValid(java.lang.Class)
	 */
	public boolean isValid(Class<?> taskClass) {
		this.taskAnnotationReader.loadTask(taskClass);
		if (this.taskAnnotationReader.hasTaskAnnotation()) {
			if (this.taskAnnotationReader.hasTriggerAnnotation()
					|| this.taskAnnotationReader.hasTriggerProperty()) {
				return true;
			}
		}
		return false;
	}

	public void setTaskAnnotationReader(
			TaskAnnotationReader taskAnnotationReader) {
		this.taskAnnotationReader = taskAnnotationReader;
	}

}

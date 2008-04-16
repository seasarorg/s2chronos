package org.seasar.chronos.core.task.impl;

import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskValidator;

// TODO isValidはあとで見直す．
public class TaskValidatorImpl implements TaskValidator {

	private TaskAnnotationReader taskAnnotationReader;

	private TaskPropertyReader taskPropertyReader;

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.TaskValidator#isValid(java.lang.Class)
	 */
	public boolean isValid(Class<?> taskClass) {
		this.taskAnnotationReader.loadTask(taskClass);
		if (this.taskAnnotationReader.hasTaskAnnotation()
				&& this.taskAnnotationReader.hasTriggerAnnotation()) {
			return true;
		}
		return false;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see org.seasar.chronos.core.task.TaskValidator#isValid(java.lang.Object,
	 *      java.lang.Class)
	 */
	public boolean isValid(Object task, Class<?> taskClass) {
		this.taskPropertyReader.loadTask(task, taskClass);
		this.taskAnnotationReader.loadTask(taskClass);
		if (this.taskAnnotationReader.hasTaskAnnotation()
				&& this.taskAnnotationReader.hasTriggerAnnotation()
				|| this.taskAnnotationReader.hasTaskAnnotation()
				&& this.taskPropertyReader.hasTrigger()) {
			return true;
		}
		return false;
	}

	public void setTaskAnnotationReader(
			TaskAnnotationReader taskAnnotationReader) {
		this.taskAnnotationReader = taskAnnotationReader;
	}

	public void setTaskPropertyReader(TaskPropertyReader taskPropertyReader) {
		this.taskPropertyReader = taskPropertyReader;
	}

}

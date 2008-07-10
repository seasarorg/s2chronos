package org.seasar.chronos.core.task;

import java.lang.annotation.Annotation;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskAnnotationReader.TriggerAnnotationHandler;

public interface TaskTriggerFinder {

	public TaskTrigger find(Annotation[] annotations, TriggerAnnotationHandler triggerAnnotationHandler);

}

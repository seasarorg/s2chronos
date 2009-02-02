package org.seasar.chronos.core.task;

import java.lang.annotation.Annotation;

import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskAnnotationReader.TriggerAnnotationHandler;

/**
 * タスクトリガーを検索するクラスです。
 * 
 * @author j5ik2o
 */
public interface TaskTriggerFinder {
	/**
	 * タスクトリガーを検索します。
	 * 
	 * @param annotations
	 * @param triggerAnnotationHandler
	 * @return {@link TaskTrigger}
	 */
	public TaskTrigger find(Annotation[] annotations,
			TriggerAnnotationHandler triggerAnnotationHandler);
}

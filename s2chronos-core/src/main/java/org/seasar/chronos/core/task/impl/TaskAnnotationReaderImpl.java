/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.task.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.TaskTriggerFinder;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskAnnotationReaderImpl implements TaskAnnotationReader {
	private static final String METHOD_NAME_GET_TRIGGER = "getTrigger";

	private Class<?> taskClass;

	private TaskTriggerFinder taskTriggerFinder;

	public void setup(Class<?> taskClass) {
		this.taskClass = taskClass;
	}

	public boolean hasTaskAnnotation() {
		Task task = this.taskClass.getAnnotation(Task.class);
		if (task != null) {
			return true;
		}
		return false;
	}

	public boolean hasTriggerAnnotation() {
		Annotation[] annotaions = this.taskClass.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName
				.endsWith(TaskConstant.NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTriggerProperty() {
		try {
			Method method =
				ReflectionUtil.getMethod(
					this.taskClass,
					METHOD_NAME_GET_TRIGGER,
					new Class[0]);
			return method != null;
		} catch (NoSuchMethodRuntimeException ex) {
			;
		}
		return false;
	}

	public Task getTaskAnnotation() {
		Task task = this.taskClass.getAnnotation(Task.class);
		return task;
	}

	public TaskTrigger getTriggerAnnotationClass(
			TriggerAnnotationHandler triggerAnnotationHandler) {
		Annotation[] annotations = this.taskClass.getAnnotations();
		TaskTrigger taskTrigger =
			taskTriggerFinder.find(annotations, triggerAnnotationHandler);
		return taskTrigger;
	}

	/**
	 * {@link TaskTriggerFinder}を設定します。
	 * 
	 * @param taskTriggerFinder
	 *            {@link TaskTriggerFinder}
	 */
	public void setTaskTriggerFinder(TaskTriggerFinder taskTriggerFinder) {
		this.taskTriggerFinder = taskTriggerFinder;
	}
}

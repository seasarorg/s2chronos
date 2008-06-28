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

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.exception.NoSuchMethodRuntimeException;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskAnnotationReaderImpl implements TaskAnnotationReader {

	private static final String METHOD_NAME_GET_TRIGGER = "getTrigger";
	private static final String NAME_SPACE_ORG_SEASAR_CHRONOS_CORE = "org.seasar.chronos.core";
	private static final String NAME_SPACE_TRIGGER_C = ".trigger.C";
	private static final String NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX = "Trigger";
	private Class<?> taskClass;
	private NamingConvention namingConvention;

	public void loadTask(Class<?> taskClass) {
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
			if (annotationName.endsWith(NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasTriggerProperty() {
		try {
			Method method = ReflectionUtil.getMethod(this.taskClass,
					METHOD_NAME_GET_TRIGGER, new Class[0]);
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

	private Class<?> getTriggerAnnotationClass(String packageName,
			String className) {
		StringBuilder sb = new StringBuilder(packageName);
		sb.append(NAME_SPACE_TRIGGER_C);
		sb.append(className);
		Class<?> triggerClass = ReflectionUtil
				.forNameNoException(sb.toString());
		return triggerClass;
	}

	public TaskTrigger getTriggerAnnotationClass(
			TriggerAnnotationHandler triggerAnnotationHandler) {
		Annotation[] annotaions = this.taskClass.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName.endsWith(NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX)) {
				Class<?> triggerAnnotationClass = this
						.getTriggerAnnotationClass(
								NAME_SPACE_ORG_SEASAR_CHRONOS_CORE,
								annotationName);
				// 標準パッケージで見つからないなら、rootPackageから検索してみる
				if (triggerAnnotationClass == null) {
					triggerAnnotationClass = this
							.findTriggerAnnotationClassForRootPackages(annotationName);
				}
				TaskTrigger taskTrigger = triggerAnnotationHandler.process(
						annotaion, triggerAnnotationClass);
				if (taskTrigger != null) {
					return taskTrigger;
				}
			}
		}
		return null;
	}

	public Class<?> findTriggerAnnotationClassForRootPackages(
			String annotationName) {
		Class<?> result = null;
		for (String packageName : this.namingConvention.getRootPackageNames()) {
			result = this
					.getTriggerAnnotationClass(packageName, annotationName);
			if (result != null) {
				return result;
			}
		}
		return result;
	}

	public void setNamingConvention(NamingConvention namingConvention) {
		this.namingConvention = namingConvention;
	}

}

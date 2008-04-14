package org.seasar.chronos.core.task.impl;

import java.lang.annotation.Annotation;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskAnnotationReaderImpl implements TaskAnnotationReader {

	private static final String NAME_SPACE_ORG_SEASAR_CHRONOS_CORE = "org.seasar.chronos.core";
	private static final String NAME_SPACE_TRIGGER_C = ".trigger.C";
	private static final String NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX = "Trigger";
	private Class<?> taskClass;
	private NamingConvention namingConvention;

	public void loadTask(Class<?> taskClass) {

	}

	public boolean hasTaskAnnotation() {
		Task task = taskClass.getAnnotation(Task.class);
		if (task != null) {
			return true;
		}
		return false;
	}

	public boolean hasTriggerAnnotation() {
		Annotation[] annotaions = taskClass.getAnnotations();
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

	public Task getTaskAnnotation() {
		Task task = taskClass.getAnnotation(Task.class);
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

	public Class<?> getTriggerAnnotationClass() {
		Annotation[] annotaions = taskClass.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName.endsWith(NAME_SPACE_TRIGGER_ANNOTATION_SUFFIX)) {
				Class<?> triggerClass = getTriggerAnnotationClass(
						NAME_SPACE_ORG_SEASAR_CHRONOS_CORE, annotationName);
				// 標準パッケージで見つからないなら、rootPackageから検索してみる
				if (triggerClass == null) {
					triggerClass = this
							.findTriggerAnnotationClassForRootPackages(annotationName);
				}
				return triggerClass;
			}
		}
		return null;
	}

	public Class<?> findTriggerAnnotationClassForRootPackages(
			String annotationName) {
		Class<?> result = null;
		for (String packageName : this.namingConvention.getRootPackageNames()) {
			result = getTriggerAnnotationClass(packageName, annotationName);
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

package org.seasar.chronos.core.task.impl;

import java.lang.annotation.Annotation;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskAnnotationReader;
import org.seasar.framework.convention.NamingConvention;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class TaskAnnotaionReaderImpl implements TaskAnnotationReader {

	private final Class<?> taskClass;
	private static final String ORG_SEASAR_CHRONOS_CORE = "org.seasar.chronos.core";
	private static final String TRIGGER_ANNOTATION_SUFFIX = "Trigger";
	private static final String TRIGGER_C = ".trigger.C";
	private NamingConvention namingConvention;

	public TaskAnnotaionReaderImpl(Class<?> taskClass) {
		this.taskClass = taskClass;
	}

	public boolean hasTask() {
		Task task = this.taskClass.getAnnotation(Task.class);
		if (task != null) {
			return true;
		}
		return false;
	}

	public boolean hasTrigger() {
		Annotation[] annotaions = this.taskClass.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName.endsWith(TRIGGER_ANNOTATION_SUFFIX)) {
				return true;
			}
		}
		return false;
	}

	public Task getTask() {
		Task task = this.taskClass.getAnnotation(Task.class);
		return task;
	}

	private static Class<?> getTriggerClass(String packageName, String className) {
		StringBuilder sb = new StringBuilder(packageName);
		sb.append(TRIGGER_C);
		sb.append(className);
		Class<?> triggerClass = ReflectionUtil
				.forNameNoException(sb.toString());
		return triggerClass;
	}

	public Class<?> getTrigger() {
		Annotation[] annotaions = this.taskClass.getAnnotations();
		for (Annotation annotaion : annotaions) {
			Class<?> annotaionClass = annotaion.annotationType();
			String annotationName = annotaionClass.getSimpleName();
			// サフィックスがTriggerなアノテーションを検索する
			if (annotationName.endsWith(TRIGGER_ANNOTATION_SUFFIX)) {
				Class<?> triggerClass = getTriggerClass(
						ORG_SEASAR_CHRONOS_CORE, annotationName);
				// 標準パッケージで見つからないなら、rootPackageから検索してみる
				if (triggerClass == null) {
					triggerClass = this
							.findTriggerForRootPackages(annotationName);
				}
				return triggerClass;
			}
		}
		return null;
	}

	public Class<?> findTriggerForRootPackages(String annotationName) {
		Class<?> result = null;
		for (String packageName : this.namingConvention.getRootPackageNames()) {
			result = getTriggerClass(packageName, annotationName);
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

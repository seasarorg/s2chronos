package org.seasar.chronos.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.seasar.chronos.core.annotation.task.Task;

public final class TaskClassUtil {

	public static final boolean isTask(Class<?> taskClazz) {
		Task task = taskClazz.getAnnotation(Task.class);
		if (task != null) {
			for (final Annotation ann : taskClazz.getAnnotations()) {
				if (!Task.class.equals(ann.annotationType())
						&& ann.annotationType().getName().endsWith("Trigger")) {
					return true;
				}
			}
			for (Method method : taskClazz.getMethods()) {
				if (method.getName().equals("getTrigger")) {
					return true;
				}
			}
		}
		return false;
	}

}

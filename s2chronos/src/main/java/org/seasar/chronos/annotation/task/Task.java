package org.seasar.chronos.annotation.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Task {

	public static final String TASK_NAME_NULL = "";

	String name() default TASK_NAME_NULL;

}

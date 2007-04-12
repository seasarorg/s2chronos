package org.seasar.chronos.core.annotation.task.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.chronos.core.annotation.type.JoinType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JoinTask {

	JoinType value() default JoinType.Wait;

}

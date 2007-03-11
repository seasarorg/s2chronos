package org.seasar.chronos.annotation.job.method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.seasar.chronos.annotation.type.JoinType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Join {

	JoinType value() default JoinType.Wait;

}

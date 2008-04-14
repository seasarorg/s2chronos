package org.seasar.chronos.core.task;

public interface TaskValidator {

	public boolean isValid(Object task, Class<?> taskClass);

}

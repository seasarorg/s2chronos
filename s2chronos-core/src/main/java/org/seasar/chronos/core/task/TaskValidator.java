package org.seasar.chronos.core.task;

/**
 * タスク
 * 
 * @author junichi
 * 
 */
public interface TaskValidator {

	/**
	 * タスククラスであるかどうかを判定します．
	 * 
	 * @param taskClass
	 * @return
	 */
	public boolean isValid(Class<?> taskClass);

	/**
	 * タスククラスであるかどうかを判定します．
	 * 
	 * @param task
	 * @param taskClass
	 * @return
	 */
	public boolean isValid(Object task, Class<?> taskClass);

}

package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.util.StringUtil;

public class TaskGetTaskNamePropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		String result = null;
		TaskPropertyReader tpr = this.getTaskPropertyReader(methodInvocation);
		String taskName = (String) methodInvocation.proceed();
		if (taskName != null) {
			return taskName;
		}
		Class<?> taskClass = tpr.getTaskClass();
		Task taskAnnotation = taskClass.getAnnotation(Task.class);
		if (taskAnnotation != null
				&& !StringUtil.isEmpty(taskAnnotation.name())) {
			return taskAnnotation.name();
		}
		Component componentAnnotation = taskClass.getAnnotation(Component.class);
		if (componentAnnotation != null
				&& !StringUtil.isEmpty(componentAnnotation.name())) {
			return componentAnnotation.name();
		}
		result = taskClass.getCanonicalName();
		return result;
	}
}

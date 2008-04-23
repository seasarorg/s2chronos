package org.seasar.chronos.core.task.command.impl;

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
		Class<?> clazz = tpr.getTaskClass();
		Task task = clazz.getAnnotation(Task.class);
		if (task != null && !StringUtil.isEmpty(task.name())) {
			return task.name();
		}
		Component component = clazz.getAnnotation(Component.class);
		if (component != null && !StringUtil.isEmpty(component.name())) {
			return component.name();
		}
		result = clazz.getCanonicalName();
		return result;
	}
}

package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.TaskPropertyWriter;
import org.seasar.chronos.core.task.handler.TaskPropertyHandler;

public abstract class AbstractTaskPropertyHandler implements
		TaskPropertyHandler {

	protected TaskPropertyReader getTaskPropertyReader(
			MethodInvocation methodInvocation) {
		TaskPropertyReader tpr = (TaskPropertyReader) methodInvocation
				.getThis();
		return tpr;
	}

	protected TaskPropertyWriter getTaskPropertyWriter(
			MethodInvocation methodInvocation) {
		TaskPropertyWriter tpr = (TaskPropertyWriter) methodInvocation
				.getThis();
		return tpr;
	}

}

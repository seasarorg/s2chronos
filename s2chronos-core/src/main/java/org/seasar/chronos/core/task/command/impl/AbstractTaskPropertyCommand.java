package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.command.TaskPropertyCommand;

public abstract class AbstractTaskPropertyCommand implements
		TaskPropertyCommand {

	protected TaskPropertyReader getTaskPropertyReader(
			MethodInvocation methodInvocation) {
		TaskPropertyReader tpr = (TaskPropertyReader) methodInvocation
				.getThis();
		return tpr;
	}

}

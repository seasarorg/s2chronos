package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.command.TaskPropertCommand;

public abstract class AbstractTaskPropertyCommand implements
		TaskPropertCommand {

	protected TaskPropertyReader getTaskPropertyReader(
			MethodInvocation methodInvocation) {
		TaskPropertyReader tpr = (TaskPropertyReader) methodInvocation
				.getThis();
		return tpr;
	}

}

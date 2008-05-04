package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskPropertyReader;

public abstract class AbstractTaskPropertyWriteHandler extends
		AbstractTaskPropertyHandler {

	private TaskPropertyReader taskPropertyReader;

	@Override
	protected TaskPropertyReader getTaskPropertyReader(
			MethodInvocation methodInvocation) {
		return taskPropertyReader;
	}

	public void setTaskPropertyReader(TaskPropertyReader taskPropertyReader) {
		this.taskPropertyReader = taskPropertyReader;
	}

}

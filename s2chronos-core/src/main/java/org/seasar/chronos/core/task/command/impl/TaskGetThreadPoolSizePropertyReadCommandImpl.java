package org.seasar.chronos.core.task.command.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;

public class TaskGetThreadPoolSizePropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		int threadPoolSize = 1;
		TaskThreadPool taskThreadPool = this.getTaskPropertyReader(
				methodInvocation).getThreadPool(null);
		if (taskThreadPool == null) {
			return methodInvocation.proceed();
		} else {
			threadPoolSize = taskThreadPool.getThreadPoolSize();
		}
		return threadPoolSize;
	}

}

package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;

public class TaskGetThreadPoolSizePropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		Object[] param = methodInvocation.getArguments();
		int threadPoolSize = (Integer) param[0];
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

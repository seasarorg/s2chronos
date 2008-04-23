package org.seasar.chronos.core.task.handler.impl;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;

public class TaskGetThreadPoolTypePropertyReadCommandImpl extends
		AbstractTaskPropertyCommand {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		ThreadPoolType threadPoolType = null;
		TaskThreadPool taskThreadPool = this.getTaskPropertyReader(
				methodInvocation).getThreadPool(null);
		if (taskThreadPool == null) {
			return methodInvocation.proceed();
		} else {
			threadPoolType = taskThreadPool.getThreadPoolType();
		}
		return threadPoolType;
	}

}

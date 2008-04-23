package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskGetThreadPoolTypePropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

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

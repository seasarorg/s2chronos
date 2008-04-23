package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;

public class TaskGetThreadPoolSizePropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

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

package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyWriteHandler;
import org.seasar.chronos.core.task.handler.impl.property.PropertyCache;

public class TaskSetExecutedPropertyWriteHandlerImpl extends
		AbstractTaskPropertyWriteHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		if (this.getTaskPropertyWriter(methodInvocation).hasExecuted()) {
			return methodInvocation.proceed();
		} else {
			// triggerプロパティが存在しないときの設定は，キャッシュに保管する
			Object task = this.getTaskPropertyWriter(methodInvocation)
					.getTask();
			PropertyCache propertyCache = PropertyCache.getInstance(task);
			propertyCache.put(TaskConstant.PROPERTY_NAME_EXECUTED,
					methodInvocation.getArguments()[0]);
			return null;
		}
	}

}

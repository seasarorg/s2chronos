package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;
import org.seasar.chronos.core.task.handler.impl.property.PropertyCache;

public class TaskIsExecutedPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		// まずPropetyCacheにプロパティがないか取り出します．あれば返します．
		Object task = this.getTaskPropertyReader(methodInvocation).getTask();
		PropertyCache propertyCache = PropertyCache.getInstance(task);
		Object executed = propertyCache
				.get(TaskConstant.PROPERTY_NAME_EXECUTED);
		if (executed != null) {
			return executed;
		}
		return methodInvocation.proceed();
	}

}

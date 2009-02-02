package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;
import org.seasar.chronos.core.task.handler.impl.property.PropertyCache;

public class TaskIsExecutingPropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		boolean result = false;
		TaskTrigger trigger = this.getTaskPropertyReader(methodInvocation)
				.getTrigger(null);
		if (trigger != null) {
			result = trigger.isExecuting();
		}
		// まずPropetyCacheにプロパティがないか取り出します．あれば返します．
		Object task = this.getTaskPropertyReader(methodInvocation).getTask();
		PropertyCache propertyCache = PropertyCache.getInstance(task);
		Object executed = propertyCache
				.get(TaskConstant.PROPERTY_NAME_EXECUTING);
		if (executed != null) {
			return result || (Boolean) executed;
		}
		return result || (Boolean) methodInvocation.proceed();
	}

}

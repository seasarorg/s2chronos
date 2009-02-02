package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyWriteHandler;
import org.seasar.chronos.core.task.handler.impl.property.PropertyCache;

public class TaskSetExecutingPropertyWriteHandlerImpl extends
		AbstractTaskPropertyWriteHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		if (this.getTaskPropertyWriter(methodInvocation).hasExecuting()) {
			return methodInvocation.proceed();
		} else {
			// triggerプロパティが存在しないときの設定は，キャッシュに保管する
			Object task = this.getTaskPropertyWriter(methodInvocation)
					.getTask();
			PropertyCache propertyCache = PropertyCache.getInstance(task);
			propertyCache.put(TaskConstant.PROPERTY_NAME_EXECUTING,
					methodInvocation.getArguments()[0]);
		}
		// トリガーが存在する場合は，値を更新する
		TaskTrigger trigger = this.getTaskPropertyWriter(methodInvocation)
				.getTaskPropertyReader().getTrigger(null);
		if (trigger != null) {
			Boolean flag = (Boolean) methodInvocation.getArguments()[0];
			trigger.setExecuting(flag);
		}
		return null;
	}
}

/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.task.handler.impl.property.write;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.model.TaskTrigger;
import org.seasar.chronos.core.task.TaskConstant;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyWriteHandler;
import org.seasar.chronos.core.task.handler.impl.property.PropertyCache;

public class TaskSetExecutedPropertyWriteHandlerImpl extends
		AbstractTaskPropertyWriteHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		if (this.getTaskPropertyWriter(methodInvocation).hasExecuted()) {
			return methodInvocation.proceed();
		} else {
			// setExecutedプロパティが存在しないときの設定は，キャッシュに保管する
			Object task = this.getTaskPropertyWriter(methodInvocation)
					.getTask();
			PropertyCache propertyCache = PropertyCache.getInstance(task);
			propertyCache.put(TaskConstant.PROPERTY_NAME_EXECUTED,
					methodInvocation.getArguments()[0]);

		}
		// トリガーが存在する場合は，値を更新する
		TaskTrigger trigger = this.getTaskPropertyWriter(methodInvocation)
				.getTaskPropertyReader().getTrigger(null);
		if (trigger != null) {
			Boolean flag = (Boolean) methodInvocation.getArguments()[0];
			trigger.setExecuted(flag);
		}
		return null;
	}
}

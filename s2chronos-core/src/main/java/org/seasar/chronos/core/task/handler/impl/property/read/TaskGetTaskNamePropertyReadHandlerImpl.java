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
package org.seasar.chronos.core.task.handler.impl.property.read;

import org.aopalliance.intercept.MethodInvocation;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.task.TaskPropertyReader;
import org.seasar.chronos.core.task.handler.impl.AbstractTaskPropertyHandler;
import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.util.StringUtil;

public class TaskGetTaskNamePropertyReadHandlerImpl extends
		AbstractTaskPropertyHandler {

	public Object execute(MethodInvocation methodInvocation) throws Throwable {
		String result = null;
		TaskPropertyReader tpr = this.getTaskPropertyReader(methodInvocation);
		String taskName = (String) methodInvocation.proceed();
		if (taskName != null) {
			return taskName;
		}
		Class<?> taskClass = tpr.getTaskClass();
		Task taskAnnotation = taskClass.getAnnotation(Task.class);
		if (taskAnnotation != null
				&& !StringUtil.isEmpty(taskAnnotation.name())) {
			return taskAnnotation.name();
		}
		Component componentAnnotation = taskClass
				.getAnnotation(Component.class);
		if (componentAnnotation != null
				&& !StringUtil.isEmpty(componentAnnotation.name())) {
			return componentAnnotation.name();
		}
		result = taskClass.getCanonicalName();
		return result;
	}
}

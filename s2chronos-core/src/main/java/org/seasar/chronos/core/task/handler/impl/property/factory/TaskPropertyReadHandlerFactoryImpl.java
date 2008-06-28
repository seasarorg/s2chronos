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
package org.seasar.chronos.core.task.handler.impl.property.factory;

import java.lang.reflect.Method;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;
import org.seasar.chronos.core.task.handler.factory.AbstractTaskPropertyHandlerFactory;
import org.seasar.framework.container.S2Container;

public class TaskPropertyReadHandlerFactoryImpl extends
		AbstractTaskPropertyHandlerFactory {

	private S2Container s2Container;

	public TaskPropertyHandler create(Method method) {
		if (method == null) {
			return null;
		}
		String componentName = taskPropertyHandlerMap.get(method.getName());
		TaskPropertyHandler result = (TaskPropertyHandler) s2Container
				.getComponent(componentName);
		return result;
	}

	public void setS2Container(S2Container container) {
		s2Container = container;
	}

}

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

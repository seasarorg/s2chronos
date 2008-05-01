package org.seasar.chronos.core.task.handler.factory;

import java.util.HashMap;
import java.util.Map;

import org.seasar.chronos.core.task.handler.TaskPropertyHandler;

public abstract class AbstractTaskPropertyHandlerFactory implements
		TaskPropertyHandlerFactory {

	protected Map<String, TaskPropertyHandler> taskPropertyHandlerMap = new HashMap<String, TaskPropertyHandler>();

	public void putTaskPropertyHandler(String key, TaskPropertyHandler handler) {
		taskPropertyHandlerMap.put(key, handler);
	}

}

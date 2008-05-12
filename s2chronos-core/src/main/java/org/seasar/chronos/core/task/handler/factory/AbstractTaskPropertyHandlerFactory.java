package org.seasar.chronos.core.task.handler.factory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTaskPropertyHandlerFactory implements
		TaskPropertyHandlerFactory {

	protected Map<String, String> taskPropertyHandlerMap = new HashMap<String, String>();

	public void putTaskPropertyHandler(String key, String compoenentName) {
		taskPropertyHandlerMap.put(key, compoenentName);
	}

}

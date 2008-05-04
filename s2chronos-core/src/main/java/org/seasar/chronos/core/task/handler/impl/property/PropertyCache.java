package org.seasar.chronos.core.task.handler.impl.property;

import java.util.HashMap;
import java.util.Map;

public final class PropertyCache {

	private static Map<Object, PropertyCache> propertyCacheInstanceMap = new HashMap<Object, PropertyCache>();

	private Map<String, Object> propertyCache = new HashMap<String, Object>();

	private Object selfKey;

	public Object getSelfKey() {
		return selfKey;
	}

	private PropertyCache(Object selfKey) {
		this.selfKey = selfKey;
	}

	/**
	 * キーに応じたプロパティキャッシュを返します．
	 * 
	 * @param key
	 * @return
	 */
	public static PropertyCache getInstance(Object key) {
		PropertyCache propertyCacheInstance = propertyCacheInstanceMap.get(key);
		if (propertyCacheInstance == null) {
			synchronized (PropertyCache.class) {
				if (propertyCacheInstance == null) {
					propertyCacheInstance = new PropertyCache(key);
					propertyCacheInstanceMap.put(key, propertyCacheInstance);
				}
			}
		}
		return propertyCacheInstance;
	}

	public Object get(String key) {
		return this.propertyCache.get(key);
	}

	public void put(String key, Object value) {
		this.propertyCache.put(key, value);
	}

}

package org.seasar.chronos.core.task.handler.impl.property;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PropertyCache {

	private static Map<Object, PropertyCache> propertyCacheInstanceMap = new ConcurrentHashMap<Object, PropertyCache>();

	private Map<String, Object> propertyCache = new ConcurrentHashMap<String, Object>();

	private Object selfKey;

	/**
	 * セルフキーを返します．
	 * 
	 * @return セルフキー
	 */
	public Object getSelfKey() {
		return selfKey;
	}

	/**
	 * コンストラクタです．
	 * 
	 * @param selfKey
	 *            セルフキー
	 */
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

	/**
	 * キーに対応するプロパティを返します．
	 * 
	 * @param key
	 *            キー
	 * @return プロパティ
	 */
	public Object get(String key) {
		return this.propertyCache.get(key);
	}

	/**
	 * キーとプロパティを設定します．
	 * 
	 * @param key
	 *            キー
	 * @param value
	 *            プロパティ
	 */
	public void put(String key, Object value) {
		this.propertyCache.put(key, value);
	}

	public boolean remove(String key) {
		return this.propertyCache.remove(key) == null ? false : true;
	}

	public void clear() {
		this.propertyCache.clear();
	}

}

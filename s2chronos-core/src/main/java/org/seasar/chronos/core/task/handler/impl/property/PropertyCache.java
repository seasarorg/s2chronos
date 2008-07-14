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
package org.seasar.chronos.core.task.handler.impl.property;

import java.util.Map;

import org.seasar.framework.util.tiger.CollectionsUtil;

public final class PropertyCache {

	private static Map<Object, PropertyCache> propertyCacheInstanceMap = CollectionsUtil
			.newConcurrentHashMap();

	private final Map<String, Object> propertyCache = CollectionsUtil
			.newConcurrentHashMap();

	private final Object selfKey;

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

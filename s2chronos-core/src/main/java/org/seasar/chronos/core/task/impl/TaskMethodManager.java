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
package org.seasar.chronos.core.task.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.framework.util.tiger.CollectionsUtil;

public class TaskMethodManager {

	private static final long serialVersionUID = -3749881053983590510L;

	private static final String NO_GROUP = "noGroup";

	private final Class<?> clazz;

	private final String prefixMethodName;

	private final HashMap<String, HashMap<String, Method>> methodMap = CollectionsUtil.newHashMap();

	private final HashMap<String, ArrayList<Method>> methodList = CollectionsUtil.newHashMap();

	private final ArrayList<String> groupList = CollectionsUtil.newArrayList();

	private final ArrayList<Method> methodAllList = CollectionsUtil.newArrayList();

	public TaskMethodManager(Class<?> clazz, String prefixMethodName) {
		this.clazz = clazz;
		this.prefixMethodName = prefixMethodName;
		this.initialize();
	}

	public Map<String, Method> getMethodMapByNoGroup() {
		return this.methodMap.get(NO_GROUP);
	}

	public Map<String, Method> getMethodMap(String groupName) {
		return this.methodMap.get(groupName);
	}

	public List<Method> getMethodList(String groupName) {
		return this.methodList.get(groupName);
	}

	public List<String> getGroupList() {
		return this.groupList;
	}

	public boolean existGroup(String groupName) {
		return this.methodMap.get(groupName) != null;
	}

	public List<Method> getAllMethodList() {
		return this.methodAllList;
	}

	private void initialize() {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith(prefixMethodName)) {
				TaskMethodMetaData md = new TaskMethodMetaData(method);
				String groupName = md.getGroupName();
				if (md == null) {
					groupName = NO_GROUP;
				}
				HashMap<String, Method> jobMethodMap = methodMap.get(groupName);
				ArrayList<Method> jobMethodList = methodList.get(groupName);
				if (null == jobMethodMap) {
					jobMethodMap = new HashMap<String, Method>();
					jobMethodList = new ArrayList<Method>();
					methodMap.put(groupName, jobMethodMap);
					methodList.put(groupName, jobMethodList);
					groupList.add(groupName);
				}
				jobMethodMap.put(methodName, method);
				jobMethodList.add(method);
				methodAllList.add(method);
			}
		}
	}

}

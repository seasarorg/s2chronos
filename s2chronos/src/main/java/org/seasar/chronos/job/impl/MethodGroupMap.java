package org.seasar.chronos.job.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodGroupMap {

	private static final String NO_GROUP = "noGroup";

	private final Class clazz;

	private final String prefixMethodName;

	private HashMap<String, HashMap<String, Method>> methodMap = new HashMap<String, HashMap<String, Method>>();

	public MethodGroupMap(Class clazz, String prefixMethodName) {
		this.clazz = clazz;
		this.prefixMethodName = prefixMethodName;
		this.initialize();
	}

	public Map<String, Method> getMethodMapByNoGroup() {
		return methodMap.get(NO_GROUP);
	}

	public Map<String, Method> getMethodMap(String groupName) {
		return methodMap.get(groupName);
	}

	public boolean existGroup(String groupName) {
		return methodMap.get(groupName) != null;
	}

	private void initialize() {
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith(prefixMethodName)) {
				JobMethodMetaData md = new JobMethodMetaData(method);
				String groupName = md.getGroupName();
				if (md == null) {
					groupName = NO_GROUP;
				}
				HashMap<String, Method> jobMethodMap = methodMap.get(groupName);
				if (null == jobMethodMap) {
					jobMethodMap = new HashMap<String, Method>();
					methodMap.put(groupName, jobMethodMap);
				}
				jobMethodMap.put(methodName, method);
			}
		}
	}

}

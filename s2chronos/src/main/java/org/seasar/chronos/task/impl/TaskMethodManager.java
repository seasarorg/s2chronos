package org.seasar.chronos.task.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskMethodManager {

	private static final String NO_GROUP = "noGroup";

	private final Class clazz;

	private final String prefixMethodName;

	private HashMap<String, HashMap<String, Method>> methodMap = new HashMap<String, HashMap<String, Method>>();

	private HashMap<String, ArrayList<Method>> methodList = new HashMap<String, ArrayList<Method>>();

	private ArrayList<String> groupList = new ArrayList<String>();

	private ArrayList<Method> methodAllList = new ArrayList<Method>();

	public TaskMethodManager(Class clazz, String prefixMethodName) {
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

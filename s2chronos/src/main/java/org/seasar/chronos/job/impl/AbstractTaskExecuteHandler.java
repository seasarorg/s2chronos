package org.seasar.chronos.job.impl;

import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.TaskExecuteHandler;

public abstract class AbstractTaskExecuteHandler implements TaskExecuteHandler {

	private MethodInvoker methodInvoker;

	public MethodGroupMap methodGroupMap;

	public abstract boolean handleRequest(String startTaskName)
			throws Throwable;

	public void setMethodInvoker(MethodInvoker methodInvoker) {
		this.methodInvoker = methodInvoker;
	}

	public MethodInvoker getMethodInvoker() {
		return methodInvoker;
	}

	public MethodGroupMap getMethodGroupMap() {
		return methodGroupMap;
	}

	public void setMethodGroupMap(MethodGroupMap methodGroupMap) {
		this.methodGroupMap = methodGroupMap;
	}
}

package org.seasar.chronos.job.impl;

import org.seasar.chronos.delegate.MethodInvoker;
import org.seasar.chronos.job.TaskExecuteHandler;
import org.seasar.chronos.job.Transition;

public abstract class AbstractTaskExecuteHandler implements TaskExecuteHandler {

	private MethodInvoker methodInvoker;

	public MethodGroupMap methodGroupMap;

	public abstract Transition handleRequest(String startTaskName)
			throws InterruptedException;

	public void setMethodInvoker(MethodInvoker methodInvoker) {
		this.methodInvoker = methodInvoker;
	}

	public MethodInvoker getMethodInvoker() {
		return methodInvoker;
	}

	public void setMethodGroupMap(MethodGroupMap methodGroupMap) {
		this.methodGroupMap = methodGroupMap;
	}

	public MethodGroupMap getMethodGroupMap() {
		return methodGroupMap;
	}
}

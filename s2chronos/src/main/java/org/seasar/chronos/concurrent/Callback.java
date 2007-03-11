package org.seasar.chronos.concurrent;

public class Callback {

	private Object target;

	private String methodName;

	public Callback(Object target) {
		this.target = target;
	}

	public Callback(Object target, String methodName) {
		this.target = target;
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object getTarget() {
		return target;
	}

}
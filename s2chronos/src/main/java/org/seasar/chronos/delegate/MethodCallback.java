package org.seasar.chronos.delegate;

public class MethodCallback {

	private Object target;

	private String methodName;

	public MethodCallback(Object target) {
		this.target = target;
	}

	public MethodCallback(Object target, String methodName) {
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
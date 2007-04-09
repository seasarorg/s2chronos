package org.seasar.chronos.delegate;

public class MethodCallback {

	private static final long serialVersionUID = -6508698553780517897L;

	private Object target = null;

	private String methodName = null;

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

	public Class getTargetClass() {
		return target.getClass();
	}

}
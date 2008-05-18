package org.seasar.chronos.core.exception;

public class InvocationTargetRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvocationTargetRuntimeException() {

	}

	public InvocationTargetRuntimeException(String message) {
		super(message);
	}

	public InvocationTargetRuntimeException(Throwable cause) {
		super(cause);
	}

	public InvocationTargetRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}

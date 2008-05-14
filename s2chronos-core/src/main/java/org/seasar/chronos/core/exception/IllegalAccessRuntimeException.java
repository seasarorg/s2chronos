package org.seasar.chronos.core.exception;

public class IllegalAccessRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IllegalAccessRuntimeException() {
	}

	public IllegalAccessRuntimeException(String message) {
		super(message);
	}

	public IllegalAccessRuntimeException(Throwable cause) {
		super(cause);
	}

	public IllegalAccessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}

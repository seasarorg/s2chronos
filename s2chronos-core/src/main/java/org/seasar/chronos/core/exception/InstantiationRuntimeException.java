package org.seasar.chronos.core.exception;

public class InstantiationRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InstantiationRuntimeException() {

	}

	public InstantiationRuntimeException(String message) {
		super(message);
	}

	public InstantiationRuntimeException(Throwable cause) {
		super(cause);
	}

	public InstantiationRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}

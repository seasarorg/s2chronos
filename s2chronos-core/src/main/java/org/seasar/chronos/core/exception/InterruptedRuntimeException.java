package org.seasar.chronos.core.exception;

public class InterruptedRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -3539763962622551567L;

	public InterruptedRuntimeException() {
	}

	public InterruptedRuntimeException(String message) {
		super(message);
	}

	public InterruptedRuntimeException(Throwable cause) {
		super(cause);
	}

	public InterruptedRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

}

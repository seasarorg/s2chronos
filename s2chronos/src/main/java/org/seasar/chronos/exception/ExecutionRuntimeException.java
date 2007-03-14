package org.seasar.chronos.exception;

public class ExecutionRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -1035724208098066740L;

	public ExecutionRuntimeException() {
	}

	public ExecutionRuntimeException(String arg0) {
		super(arg0);
	}

	public ExecutionRuntimeException(Throwable arg0) {
		super(arg0);
	}

	public ExecutionRuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}

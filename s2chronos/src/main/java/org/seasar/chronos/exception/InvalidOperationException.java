package org.seasar.chronos.exception;

public class InvalidOperationException extends RuntimeException {

	private static final long serialVersionUID = -8949428966568720276L;

	public InvalidOperationException() {
	}

	public InvalidOperationException(String arg0) {
		super(arg0);
	}

	public InvalidOperationException(Throwable arg0) {
		super(arg0);
	}

	public InvalidOperationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}

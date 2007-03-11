package org.seasar.chronos.delegate;

import java.util.concurrent.Future;

public class AsyncResult {

	private Future<Object> future = null;

	private Object state = null;

	public Future<Object> getFuture() {
		return future;
	}

	public void setFuture(Future<Object> future) {
		this.future = future;
	}

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}

}

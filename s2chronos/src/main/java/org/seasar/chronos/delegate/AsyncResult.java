package org.seasar.chronos.delegate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncResult {

	private Future<Object> future = null;

	private Object state = null;

	Future<Object> getFuture() {
		return future;
	}

	void setFuture(Future<Object> future) {
		this.future = future;
	}

	public Object getState() {
		return state;
	}

	public void setState(Object state) {
		this.state = state;
	}

	public void waitOne() throws Throwable {
		try {
			this.future.get();
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}

	public boolean isCompleted() {
		return this.future.isDone();
	}

}

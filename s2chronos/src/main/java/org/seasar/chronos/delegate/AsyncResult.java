package org.seasar.chronos.delegate;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.seasar.chronos.exception.ExecutionRuntimeException;

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

	public void waitOne() throws InterruptedException {
		try {
			this.future.get();
		} catch (CancellationException e) {
			;
		} catch (ExecutionException e) {
			throw new ExecutionRuntimeException(e);
		}
	}

	public boolean isCompleted() {
		return this.future.isDone();
	}

}

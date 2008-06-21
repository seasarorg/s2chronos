/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.delegate;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.seasar.chronos.core.exception.ExecutionRuntimeException;

public class AsyncResult {

	private static final long serialVersionUID = -5239879067321325795L;

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

	void setState(Object state) {
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

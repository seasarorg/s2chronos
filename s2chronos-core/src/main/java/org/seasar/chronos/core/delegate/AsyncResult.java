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

/**
 * 非同期結果セットです．
 * 
 * @author j5ik2o
 */
public class AsyncResult {

	private static final long serialVersionUID = -5239879067321325795L;

	private Future<Object> future = null;

	private Object state = null;

	/**
	 * {@link Future}を返します．
	 * 
	 * @return {@link Future}
	 */
	Future<Object> getFuture() {
		return future;
	}

	/**
	 * {@link Future}を設定します．
	 * 
	 * @param future
	 *            {@link Future}
	 */
	void setFuture(Future<Object> future) {
		this.future = future;
	}

	/**
	 * 非同期呼び出しの状態を返します．
	 * 
	 * @return 非同期呼び出しの状態
	 */
	public Object getState() {
		return state;
	}

	/**
	 * 非同期呼び出しの状態を設定します．
	 * 
	 * @param state
	 *            非同期呼び出しの状態
	 */
	void setState(Object state) {
		this.state = state;
	}

	/**
	 * 非同期呼び出しを待機します．
	 * 
	 * @throws InterruptedException
	 */
	public void waitOne() throws InterruptedException {
		try {
			this.future.get();
		} catch (CancellationException e) {
			;
		} catch (ExecutionException e) {
			throw new ExecutionRuntimeException(e);
		}
	}

	/**
	 * 非同期呼び出しが完了したかどうかを返します．
	 * 
	 * @return 完了した場合true,完了していない場合false
	 */
	public boolean isCompleted() {
		return this.future.isDone();
	}

}

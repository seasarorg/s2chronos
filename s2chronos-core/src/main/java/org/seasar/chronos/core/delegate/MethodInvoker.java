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

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.seasar.chronos.core.exception.ExecutionRuntimeException;
import org.seasar.chronos.core.executor.ExecutorServiceFactory;
import org.seasar.chronos.core.model.ThreadPoolType;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class MethodInvoker {
	private static final long serialVersionUID = -3755599989232609874L;

	private static Logger log = Logger.getLogger(MethodInvoker.class);

	private static final String CALLBACK_SUFFIX = "Callback";

	private final CopyOnWriteArrayList<AsyncResult> resultList =
	    CollectionsUtil.newCopyOnWriteArrayList();

	private final Class<?> targetClass;

	private final Object target;

	private final BeanDesc beanDesc;

	private final ExecutorService executorService;

	private ExecutorServiceFactory executorServiceFactory;

	private ExecutorService callbackExecutorService;

	/**
	 * コンストラクタ
	 * 
	 * @param executorService
	 *            ExecutorService
	 * @param componentDef
	 *            コンポーネント定義
	 */
	public MethodInvoker(ExecutorService executorService,
	        ComponentDef componentDef) {
		this.executorService = executorService;
		this.target = componentDef.getComponent();
		this.targetClass = componentDef.getComponentClass();
		this.beanDesc = BeanDescFactory.getBeanDesc(this.targetClass);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param executorService
	 *            ExecutorService
	 * @param target
	 *            ターゲットオブジェクト
	 * @param beanDesc
	 *            BeanDesc
	 */
	public MethodInvoker(ExecutorService executorService, Object target,
	        BeanDesc beanDesc) {
		this.executorService = executorService;
		this.beanDesc = beanDesc;
		this.target = target;
		this.targetClass = this.beanDesc.getBeanClass();
	}

	private boolean initialized;

	private synchronized void initialize() {
		if (!initialized) {
			this.callbackExecutorService =
			    executorServiceFactory.create(ThreadPoolType.SINGLE, null);
			initialized = true;
		}
	}

	public boolean awaitInvokes(long time, TimeUnit unit)
	        throws InterruptedException {
		return this.executorService.awaitTermination(time, unit);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @return 非同期結果オブジェクト
	 * @throws InterruptedException
	 */
	public AsyncResult beginInvoke(final String methodName)
	        throws InterruptedException {
		return beginInvoke(methodName, null, null, null);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param methodCallback
	 *            メソッドコールバック(メソッド名には非Publicが指定できます)
	 * @param state
	 *            ステート
	 * @return 非同期結果オブジェクト
	 * @throws InterruptedException
	 */
	public AsyncResult beginInvoke(final String methodName,
	        final MethodCallback methodCallback, final Object state)
	        throws InterruptedException {
		return beginInvoke(methodName, null, methodCallback, state);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param args
	 *            メソッドの引数
	 * @return 非同期結果オブジェクト
	 * @throws InterruptedException
	 */
	public AsyncResult beginInvoke(final String methodName, final Object[] args)
	        throws InterruptedException {
		return beginInvoke(methodName, args, null, null);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param args
	 *            メソッドの引数名
	 * @param methodCallback
	 *            メソッドコールバックオブジェクト
	 * @param state
	 *            ステート
	 * @return 非同期結果オブジェクト
	 * @throws InterruptedException
	 */
	public AsyncResult beginInvoke(final String methodName,
	        final Object[] args, final MethodCallback methodCallback,
	        final Object state) throws InterruptedException {
		this.initialize();
		final AsyncResult asyncResult = new AsyncResult();
		this.resultList.add(asyncResult);
		asyncResult.setState(state);
		final Future<Object> future =
		    this.executorService.submit(new Callable<Object>() {
			    public Object call() throws Exception {
				    // 対象メソッドを実行
				    Object result = null;
				    try {
					    result = invoke(methodName, args);
					    if (methodCallback != null) {
						    // さらにコールバックをスレッドプールから実行
						    callbackExecutorService
						        .submit(new Callable<Void>() {
							        public Void call() throws Exception {
								        callbackHandler(
								            methodName,
								            methodCallback,
								            asyncResult);
								        return null;
							        }
						        });
						    // futureCallback.get();
					    }
				    } finally {
					    resultList.remove(asyncResult);
				    }
				    return result;
			    }
		    });
		asyncResult.setFuture(future);
		return asyncResult;
	}

	// コールバックを実行します
	private void callbackHandler(final String methodName,
	        final MethodCallback methodCallback, final AsyncResult asyncResult)
	        throws Exception {
		try {
			String callbackMethodName = methodCallback.getMethodName();
			StringBuffer sb = new StringBuffer();
			if (callbackMethodName == null) {
				sb.append(methodName);
				sb.append(CALLBACK_SUFFIX);
			} else {
				sb.append(callbackMethodName);
			}
			Method mt =
			    ReflectionUtil.getDeclaredMethod(methodCallback
			        .getTargetClass(), sb.toString(), AsyncResult.class);
			mt.setAccessible(true);
			ReflectionUtil.invoke(mt, methodCallback.getTarget(), asyncResult);
		} catch (Exception ex) {
			log.log("ECHRONOS0001", null, ex);
			throw ex;
		}
	}

	/**
	 * 非同期呼び出しをキャンセルします．
	 * 
	 * @param asyncResult
	 *            非同期結果オブジェクト
	 * @return 戻り値のオブジェクト
	 */
	public boolean cancelInvoke(AsyncResult asyncResult) {
		return cancelInvoke(asyncResult, true);
	}

	/**
	 * 非同期呼び出しをキャンセルします．
	 * 
	 * @param asyncResult
	 * @param shutdown
	 * @return
	 */
	public boolean cancelInvoke(AsyncResult asyncResult, boolean shutdown) {
		return asyncResult.getFuture().cancel(shutdown);
	}

	/**
	 * 非同期呼び出しをキャンセルします．
	 * <p>
	 * シャットダウン(即座に終了)でキャンセルします．
	 * </p>
	 */
	public void cancelInvokes() {
		this.cancelInvokes(true);
	}

	/**
	 * 非同期呼び出しをキャンセルします．
	 * 
	 * @param shutdown
	 *            シャットダウン(即座に終了)ならtrue, それ以外ならfalse
	 */
	public void cancelInvokes(boolean shutdown) {
		if (shutdown) {
			this.executorService.shutdownNow();
		} else {
			this.executorService.shutdown();
		}
	}

	/**
	 * 非同期呼び出しの戻り値を返します．<br>
	 * 非同期呼び出しが完了していない場合は，完了を待機します．
	 * 
	 * @param asyncResult
	 *            非同期結果オブジェクト
	 * @return 戻り値のオブジェクト
	 * @throws InterruptedException
	 *             例外
	 */
	public Object endInvoke(AsyncResult asyncResult)
	        throws InterruptedException {
		try {
			return asyncResult.getFuture().get();
		} catch (ExecutionException e) {
			throw new ExecutionRuntimeException(e);
		}
	}

	/**
	 * ExecutorServiceを返します．
	 * 
	 * @return ExecutorService
	 */
	public ExecutorService getExecutorService() {
		return executorService;
	}

	/**
	 * メソッドを返します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @return メソッドオブジェクト
	 */
	public Method getMethod(String methodName) {
		return this.beanDesc.getMethod(methodName);
	}

	/**
	 * メソッドの存在の有無を返します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @return あり=true, なし=false
	 */
	public boolean hasMethod(String methodName) {
		return this.beanDesc.hasMethod(methodName);
	}

	/**
	 * 指定したメソッドを同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @return 戻り値
	 */
	public Object invoke(String methodName) {
		return invoke(methodName, null);
	}

	/**
	 * 指定したメソッドを同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param args
	 *            メソッド引数
	 * @return 戻り値
	 */
	public Object invoke(final String methodName, final Object[] args) {
		Object result = this.beanDesc.invoke(this.target, methodName, args);
		return result;
	}

	/**
	 * 非同期呼び出しの終了を待機します．
	 * 
	 * @throws InterruptedException
	 */
	public void waitInvokes() throws InterruptedException {
		for (AsyncResult ar : resultList) {
			this.endInvoke(ar);
		}
	}

	public void setExecutorServiceFactory(
	        ExecutorServiceFactory executorServiceFactory) {
		this.executorServiceFactory = executorServiceFactory;
	}
}

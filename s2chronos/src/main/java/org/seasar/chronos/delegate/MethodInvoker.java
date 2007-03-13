package org.seasar.chronos.delegate;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.tiger.ReflectionUtil;

public class MethodInvoker {

	private static Logger log = Logger.getLogger(MethodInvoker.class);

	private static final String CALLBACK_SUFFIX = "Callback";

	private Class targetClass;

	private Object target;

	private BeanDesc beanDesc;

	private ExecutorService executorService;

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
	 * ExecutorServiceを返します．
	 * 
	 * @return ExecutorService
	 */
	public ExecutorService getExecutorService() {
		return executorService;
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
		Object result = (Object) this.beanDesc.invoke(this.target, methodName,
				args);
		return result;
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @return 非同期結果オブジェクト
	 */
	public AsyncResult beginInvoke(final String methodName) {
		return beginInvoke(methodName, null, null, null);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param args
	 *            メソッドの引数
	 * @return 非同期結果オブジェクト
	 */
	public AsyncResult beginInvoke(final String methodName, final Object[] args) {
		return beginInvoke(methodName, args, null, null);
	}

	/**
	 * 指定したメソッドを非同期で呼び出します．
	 * 
	 * @param methodName
	 *            メソッド名
	 * @param methodCallback
	 *            メソッドコールバック(メソッド名には非Publicｇが指定できます)
	 * @param state
	 *            ステート
	 * @return 非同期結果オブジェクト
	 */
	public AsyncResult beginInvoke(final String methodName,
			final MethodCallback methodCallback, final Object state) {
		return beginInvoke(methodName, null, methodCallback, state);
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
	 */
	public AsyncResult beginInvoke(final String methodName,
			final Object[] args, final MethodCallback methodCallback,
			final Object state) {

		final AsyncResult asyncResult = new AsyncResult();

		Future<Object> future = this.executorService
				.submit(new Callable<Object>() {
					public Object call() throws Exception {
						synchronized (asyncResult) {
							asyncResult.notify();
						}
						// 対象メソッドを実行
						Object result = invoke(methodName, args);

						if (methodCallback != null) {
							ExecutorService es = Executors
									.newSingleThreadExecutor();
							// さらにコールバックをスレッドプールから実行
							es.submit(new Callable<Void>() {
								public Void call() throws Exception {
									callbackHandler(methodName, methodCallback,
											asyncResult);
									return null;
								}
							});
						}

						return result;
					}

					// コールバックを実行します
					private void callbackHandler(final String methodName,
							final MethodCallback methodCallback,
							final AsyncResult asyncResult) throws Exception {
						try {
							StringBuffer callbackMethodName = new StringBuffer(
									methodCallback.getMethodName());
							if (callbackMethodName == null) {
								callbackMethodName.append(methodName);
								callbackMethodName.append(CALLBACK_SUFFIX);
							}
							Method mt = ReflectionUtil.getDeclaredMethod(
									methodCallback.getTargetClass(),
									callbackMethodName.toString(),
									AsyncResult.class);
							mt.setAccessible(true);
							ReflectionUtil.invoke(mt, methodCallback
									.getTarget(), asyncResult);
						} catch (Exception ex) {
							log.error(ex);
							throw ex;
						}

					}
				});

		synchronized (asyncResult) {
			asyncResult.setFuture(future);
			asyncResult.setState(state);
			try {
				asyncResult.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return asyncResult;
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
		} catch (InterruptedException e) {
			log.warn(e);
			throw e;
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

	public void cancelInvokes() {
		this.cancelInvokes(true);
	}

	public void cancelInvokes(boolean shutdown) {
		if (shutdown) {
			this.executorService.shutdownNow();
		} else {
			this.executorService.shutdown();
		}
	}

	public boolean awaitInvokes(long time, TimeUnit unit)
			throws InterruptedException {
		try {
			return this.executorService.awaitTermination(time, unit);
		} catch (InterruptedException e) {
			log.warn(e);
			throw e;
		}
	}

}

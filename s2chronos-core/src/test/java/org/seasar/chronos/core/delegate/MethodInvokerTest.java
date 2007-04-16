package org.seasar.chronos.core.delegate;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

public class MethodInvokerTest extends TestCase {

	private MethodInvoker methodInvoker;

	private BeanDesc beanDesc;

	@Override
	protected void setUp() throws Exception {
		this.beanDesc = BeanDescFactory.getBeanDesc(MethodInvokerTest.class);
		methodInvoker = new MethodInvoker(Executors.newSingleThreadExecutor(),
				this, beanDesc);
		super.setUp();
	}

	public void targetMethod() {
		System.out.println("start");
		for (int i = 0; i < 10 || Thread.currentThread().isInterrupted(); i++) {
			try {
				System.out.println(i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("cancel");
				break;
			}
		}
		System.out.println("end");
	}

	public void targetMethodArgs(int n) {
		for (int i = 0; i < n; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void targetMethodArgsCallback(AsyncResult asyncResult) {
		System.out.println("CallbackArgs Finish!!!");
	}

	public void targetMethodCallback(AsyncResult asyncResult) {
		System.out.println("Callback Finish!!!");
	}

	public void testBeginInvokeString() {
		try {
			AsyncResult asyncResult = methodInvoker.beginInvoke("targetMethod");
			methodInvoker.endInvoke(asyncResult);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testBeginInvokeStringMethodCallbackObject() {
		try {
			MethodCallback methodCallback = new MethodCallback(this);
			AsyncResult asyncResult = methodInvoker.beginInvoke("targetMethod",
					methodCallback, null);
			methodInvoker.endInvoke(asyncResult);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			fail();
		}

	}

	public void testBeginInvokeStringObjectArray() {
		try {
			AsyncResult asyncResult = methodInvoker.beginInvoke(
					"targetMethodArgs", new Object[] { 100 });
			methodInvoker.endInvoke(asyncResult);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testBeginInvokeStringObjectArrayMethodCallbackObject() {
		try {
			MethodCallback methodCallback = new MethodCallback(this);
			AsyncResult asyncResult = methodInvoker.beginInvoke(
					"targetMethodArgs", new Object[] { 100 }, methodCallback,
					null);
			methodInvoker.endInvoke(asyncResult);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testCancelInvokeAsyncResult() {
		try {
			AsyncResult asyncResult = methodInvoker.beginInvoke("targetMethod");
			Thread.sleep(5000);
			methodInvoker.cancelInvoke(asyncResult);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testCancelInvokeAsyncResultBoolean() {
		try {
			AsyncResult asyncResult = methodInvoker.beginInvoke("targetMethod");
			Thread.sleep(5000);
			methodInvoker.cancelInvoke(asyncResult, false);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testCancelInvokes() {
		try {
			methodInvoker.beginInvoke("targetMethod");
			Thread.sleep(5000);
			methodInvoker.cancelInvokes();
			while (!methodInvoker.awaitInvokes(1, TimeUnit.SECONDS)) {
				System.out.println("wait");
			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testCancelInvokesBoolean() {
		try {
			methodInvoker.beginInvoke("targetMethod");
			Thread.sleep(5000);
			methodInvoker.cancelInvokes(false);
			while (!methodInvoker.awaitInvokes(1, TimeUnit.SECONDS)) {
				System.out.println("wait");
			}
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			fail();
		}
	}

	public void testGetExecutorService() {
		assertNotNull(methodInvoker.getExecutorService());
	}

	public void testGetMethod() {
		assertNotNull(methodInvoker.getMethod("targetMethod"));
	}

	public void testHasMethod() {
		methodInvoker.hasMethod("targetMethod");
	}

	public void testInvokeString() {
		methodInvoker.invoke("targetMethod");
	}

	public void testInvokeStringObjectArray() {
		methodInvoker.invoke("targetMethodArgs", new Object[] { 100 });
	}

	public void testWaitInvokes() {
		try {
			methodInvoker.beginInvoke("targetMethod");
			methodInvoker.beginInvoke("targetMethod");
			methodInvoker.beginInvoke("targetMethod");
			methodInvoker.waitInvokes();
		} catch (InterruptedException e) {
			fail();
		}
	}

}

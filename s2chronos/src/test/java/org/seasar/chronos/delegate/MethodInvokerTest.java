package org.seasar.chronos.delegate;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;

public class MethodInvokerTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private MethodInvoker target;

	protected void setUp() throws Exception {
		this.include(PATH);
		ComponentDef componentDef = this.getComponentDef(InvokeTarget.class);
		target = new MethodInvoker(Executors.newFixedThreadPool(2),
				componentDef);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHasMethod() {
		boolean ret = target.hasMethod("testA");
		assertEquals(ret, true);
	}

	public void testGetMethod() {
		Method method = target.getMethod("testA");
		assertNotNull(method);
	}

	public void testInvokeString() {
		target.invoke("testA");
	}

	public void testInvokeStringObjectArray() {
		String testValue = "testB";
		String result = (String) target.invoke("testB",
				new Object[] { testValue });
		assertEquals(result, testValue);
	}

	public void testBeginInvokeString() {
		AsyncResult ar;
		try {
			ar = target.beginInvoke("testA");
			target.endInvoke(ar);
		} catch (InterruptedException e1) {
			fail();
		}
	}

	public void testBeginInvokeStringMethodCallbackObject() {
		String testValue = "testB";
		AsyncResult ar;
		try {
			ar = target.beginInvoke("testB", new Object[] { testValue });
			assertNotNull(ar);
			String result = (String) target.endInvoke(ar);
			assertEquals(result, testValue);
		} catch (InterruptedException e1) {
			fail();
		}

	}

	@SuppressWarnings("unused")
	private void Callback(AsyncResult ar) {
		try {
			target.endInvoke(ar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void testBeginInvokeStringObjectArrayMethodCallbackObject() {
		String testValue = "testB";
		try {
			AsyncResult ar = target.beginInvoke("testB",
					new Object[] { testValue }, new MethodCallback(this,
							"Callback"), null);
			ar.waitOne();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {

		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void testCancelInvokeAsyncResult() {

		AsyncResult ar;
		try {
			ar = target.beginInvoke("testC");
			Thread.sleep(3);
			target.cancelInvoke(ar);
			Thread.sleep(5);
			assertEquals(ar.isCompleted(), true);
			ar.waitOne();
		} catch (InterruptedException e2) {
			fail();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void testCancelInvokeAsyncResultBoolean() {

		try {
			AsyncResult ar = target.beginInvoke("testC");
			Thread.sleep(3);
			target.cancelInvoke(ar, false);
			Thread.sleep(5);
			assertEquals(ar.isCompleted(), true);
			ar.waitOne();
		} catch (InterruptedException e) {
			fail();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}

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
		AsyncResult ar = target.beginInvoke("testA");
		try {
			target.endInvoke(ar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void testBeginInvokeStringMethodCallbackObject() {
		String testValue = "testB";
		AsyncResult ar = target
				.beginInvoke("testB", new Object[] { testValue });
		assertNotNull(ar);
		String result = null;
		try {
			result = (String) target.endInvoke(ar);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		assertEquals(result, testValue);
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
		AsyncResult ar = target.beginInvoke("testB",
				new Object[] { testValue },
				new MethodCallback(this, "Callback"), null);
		try {
			ar.waitOne();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void testCancelInvokeAsyncResult() {

		AsyncResult ar = target.beginInvoke("testC");

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			;
		}

		target.cancelInvoke(ar);

		try {
			Thread.sleep(5);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		assertEquals(ar.isCompleted(), true);

		try {
			ar.waitOne();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void testCancelInvokeAsyncResultBoolean() {
		AsyncResult ar = target.beginInvoke("testC");

		try {
			Thread.sleep(3);
		} catch (InterruptedException e) {
			;
		}

		target.cancelInvoke(ar, false);

		try {
			Thread.sleep(5);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		assertEquals(ar.isCompleted(), true);

		try {
			ar.waitOne();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}

	}

}

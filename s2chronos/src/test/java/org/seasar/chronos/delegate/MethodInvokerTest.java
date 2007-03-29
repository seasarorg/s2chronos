package org.seasar.chronos.delegate;

import java.lang.reflect.Method;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		} catch (InterruptedException e) {
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
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			fail();
		}
	}

	@SuppressWarnings("unused")
	private void Callback(AsyncResult ar) throws Exception {
		// try {
		// target.endInvoke(ar);
		// } catch (InterruptedException e) {
		// fail();
		// }
		throw new Exception();
	}

	public void testBeginInvokeStringObjectArrayMethodCallbackObject() {
		String testValue = "testB";
		AsyncResult ar;
		try {
			ar = target.beginInvoke("testB", new Object[] { testValue },
					new MethodCallback(this, "Callback"), null);
			ar.waitOne();
		} catch (InterruptedException e) {
			fail();
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
		} catch (InterruptedException e) {
			fail();
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
		}
	}

}

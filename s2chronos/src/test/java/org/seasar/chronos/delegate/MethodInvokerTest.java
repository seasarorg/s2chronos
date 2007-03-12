package org.seasar.chronos.delegate;

import java.util.concurrent.Executors;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.ComponentDef;
import org.seasar.s2chronos.job.ExampleJob;

public class MethodInvokerTest extends S2TestCase {

	private static final String PATH = "app.dicon";

	private MethodInvoker target;

	protected void setUp() throws Exception {
		this.include(PATH);
		ComponentDef componentDef = this.getComponentDef(ExampleJob.class);
		target = new MethodInvoker(Executors.newSingleThreadExecutor(),
				componentDef);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testHasMethod() {
		boolean ret = target.hasMethod("initialize");
		assertEquals(ret, true);
	}

	public void testGetMethod() {
		fail("まだ実装されていません。");
	}

	public void testInvokeString() {
		fail("まだ実装されていません。");
	}

	public void testInvokeStringObjectArray() {
		fail("まだ実装されていません。");
	}

	public void testBeginInvokeString() {
		fail("まだ実装されていません。");
	}

	public void testBeginInvokeStringMethodCallbackObject() {
		fail("まだ実装されていません。");
	}

	public void testBeginInvokeStringObjectArrayMethodCallbackObject() {
		fail("まだ実装されていません。");
	}

	public void testEndInvoke() {
		fail("まだ実装されていません。");
	}

	public void testCancelInvokeAsyncResult() {
		fail("まだ実装されていません。");
	}

	public void testCancelInvokeAsyncResultBoolean() {
		fail("まだ実装されていません。");
	}

}

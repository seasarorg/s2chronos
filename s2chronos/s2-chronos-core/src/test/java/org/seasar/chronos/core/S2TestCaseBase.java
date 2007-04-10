package org.seasar.chronos.core;

import org.seasar.extension.unit.S2TestCase;

public class S2TestCaseBase extends S2TestCase {

	private static final String PATH = "test.dicon";

	protected void setUp() throws Exception {
		super.setUp();
		include(PATH);
	}
}

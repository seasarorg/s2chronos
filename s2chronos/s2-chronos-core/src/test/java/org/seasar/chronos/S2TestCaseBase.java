package org.seasar.chronos;

import org.seasar.extension.unit.S2TestCase;

public class S2TestCaseBase extends S2TestCase {

	private static final String PATH = "app.dicon";

	protected void setUp() throws Exception {
		super.setUp();
		include(PATH);
	}
}

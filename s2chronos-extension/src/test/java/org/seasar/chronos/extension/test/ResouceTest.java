package org.seasar.chronos.extension.test;

import java.io.File;
import java.io.InputStream;

import org.junit.runner.RunWith;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal;
import org.seasar.framework.unit.Seasar2;
import org.seasar.framework.util.ResourceTraversal;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.ResourceTraversal.ResourceHandler;

@RunWith(Seasar2.class)
public class ResouceTest {
	public void testResource() {
		File file = ResourceUtil
				.getResourceAsFileNoException(ScriptFileTraversal.class);
		ResourceTraversal.forEach(file.getParentFile(), new ResourceHandler() {
			public void processResource(String s, InputStream inputstream) {
				System.out.println(s);
			}
		});

	}
}

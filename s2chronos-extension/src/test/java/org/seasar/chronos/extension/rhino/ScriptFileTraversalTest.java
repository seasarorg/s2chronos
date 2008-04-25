package org.seasar.chronos.extension.rhino;

import static org.junit.Assert.assertTrue;

import java.io.InputStream;

import org.junit.Test;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;
import org.seasar.framework.log.Logger;

public class ScriptFileTraversalTest {

	private final Logger log = Logger.getLogger(ScriptFileTraversalTest.class);

	private final ScriptFileTraversal scriptFileTraversal = new ScriptFileTraversal();

	@Test
	public void testForEach() {
		ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
		scriptResourceFolder.setPath("./");
		scriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);
		boolean result = scriptFileTraversal.forEach(new ScriptFileHandler() {
			public void process(String path, InputStream is) {
				String message = String.format("path = %s", path);
				log.debug(message);
			}
		});
		assertTrue(result);
	}
}

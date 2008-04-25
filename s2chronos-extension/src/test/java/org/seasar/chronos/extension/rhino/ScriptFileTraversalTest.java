package org.seasar.chronos.extension.rhino;

import java.io.InputStream;

import org.junit.Test;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;
import org.seasar.framework.log.Logger;

public class ScriptFileTraversalTest {

	private Logger log = Logger.getLogger(ScriptFileTraversalTest.class);

	private ScriptFileTraversal scriptFileTraversal = new ScriptFileTraversal();

	@Test
	public void testForEach() {
		ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
		scriptResourceFolder.setFolderClass(ScriptFileTraversalTest.class);
		scriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);
		scriptFileTraversal.forEach(new ScriptFileHandler() {
			public void process(String path, InputStream is) {
				String message = String.format("path = %s", path);
				log.debug(message);
			}
		});
	}
}

package org.seasar.chronos.extension.rhino;

import java.io.InputStream;

import org.junit.Test;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;

public class ScriptFileTraversalTest {

	private ScriptFileTraversal scriptFileTraversal;

	@Test
	public void testForEach() {
		ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
		scriptResourceFolder.setFolderClass(ScriptFileTraversalTest.class);
		scriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);
		scriptFileTraversal.forEach(new ScriptFileHandler() {
			public void process(String path, InputStream is) {

			}
		});
	}

}

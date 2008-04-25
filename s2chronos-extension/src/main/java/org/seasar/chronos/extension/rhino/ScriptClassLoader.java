package org.seasar.chronos.extension.rhino;

import java.io.InputStream;

import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;

public class ScriptClassLoader {
	public void load() {
		ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
		scriptResourceFolder.setPath("./");
		ScriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);
		boolean result = ScriptFileTraversal.forEach(new ScriptFileHandler() {
			public void process(String path, InputStream is) {

			}
		});
	}
}

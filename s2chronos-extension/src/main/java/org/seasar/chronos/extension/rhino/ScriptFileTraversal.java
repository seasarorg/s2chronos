package org.seasar.chronos.extension.rhino;

import java.io.File;
import java.io.InputStream;

import org.seasar.framework.util.ResourceTraversal;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.ResourceTraversal.ResourceHandler;

public final class ScriptFileTraversal {

	public interface ScriptFileHandler {
		void process(String path, InputStream is);
	}

	private ScriptResourceFolder scriptResourceFolder;

	public void forEach(final ScriptFileHandler handler) {
		File file = null;
		if (scriptResourceFolder.getFolderClass() != null) {
			file = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder
							.getFolderClass());
		} else {
			file = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder
							.getPath());
		}
		if (file == null) {
			return;
		}
		ResourceTraversal.forEach(file.getParentFile(), new ResourceHandler() {
			public void processResource(String path, InputStream is) {
				if (path.endsWith(".js")) {
					handler.process(path, is);
				}
			}
		});
	}

	public ScriptResourceFolder getScriptResourceFolder() {
		return scriptResourceFolder;
	}

	public void setScriptResourceFolder(
			ScriptResourceFolder scriptResourceFolder) {
		this.scriptResourceFolder = scriptResourceFolder;
	}

}

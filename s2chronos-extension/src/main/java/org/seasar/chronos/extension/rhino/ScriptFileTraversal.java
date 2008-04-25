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

	private boolean scanResult;

	private ScriptResourceFolder scriptResourceFolder;

	public boolean forEach(final ScriptFileHandler handler) {
		scanResult = false;
		File targetFile = null;
		if (scriptResourceFolder.getFolderClass() != null) {
			targetFile = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder
							.getFolderClass());
			targetFile = targetFile.getParentFile();
		} else {
			targetFile = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder
							.getPath());
		}
		if (targetFile == null) {
			return false;
		}
		ResourceTraversal.forEach(targetFile, new ResourceHandler() {
			public void processResource(String path, InputStream is) {
				if (path.endsWith(".js")) {
					scanResult = true;
					handler.process(path, is);
				}
			}
		});
		return scanResult;
	}

	public ScriptResourceFolder getScriptResourceFolder() {
		return scriptResourceFolder;
	}

	public void setScriptResourceFolder(
			ScriptResourceFolder scriptResourceFolder) {
		this.scriptResourceFolder = scriptResourceFolder;
	}

}

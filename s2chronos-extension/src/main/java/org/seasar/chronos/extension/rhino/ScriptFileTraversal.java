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

	private static boolean scanResult;

	private static ScriptResourceFolder scriptResourceFolder_;

	public static boolean forEach(final ScriptFileHandler handler) {
		scanResult = false;
		File targetFile = null;
		if (scriptResourceFolder_.getFolderClass() != null) {
			targetFile = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder_
							.getFolderClass());
			targetFile = targetFile.getParentFile();
		} else {
			targetFile = ResourceUtil
					.getResourceAsFileNoException(scriptResourceFolder_
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

	public static ScriptResourceFolder getScriptResourceFolder() {
		return scriptResourceFolder_;
	}

	public static void setScriptResourceFolder(
			ScriptResourceFolder scriptResourceFolder) {
		scriptResourceFolder_ = scriptResourceFolder;
	}

}

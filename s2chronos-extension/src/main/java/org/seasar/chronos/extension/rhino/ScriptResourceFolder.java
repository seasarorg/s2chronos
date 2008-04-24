package org.seasar.chronos.extension.rhino;

public class ScriptResourceFolder {

	private String path;
	private Class<?> folderClass;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Class<?> getFolderClass() {
		return folderClass;
	}

	public void setFolderClass(Class<?> folderClass) {
		this.folderClass = folderClass;
	}
}

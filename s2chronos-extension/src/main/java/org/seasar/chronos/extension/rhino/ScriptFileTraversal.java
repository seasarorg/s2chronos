package org.seasar.chronos.extension.rhino;

import java.io.File;
import java.io.InputStream;

import org.seasar.framework.util.ResourceTraversal;
import org.seasar.framework.util.ResourceUtil;
import org.seasar.framework.util.ResourceTraversal.ResourceHandler;

public final class ScriptFileTraversal {

	public Object forEach() {
		File file = ResourceUtil
				.getResourceAsFileNoException(ScriptFileTraversal.class);
		ResourceTraversal.forEach(file.getParentFile(), new ResourceHandler() {
			public void processResource(String path, InputStream inputstream) {
				path.endsWith(".js");
			}
		});
		return null;
	}

}

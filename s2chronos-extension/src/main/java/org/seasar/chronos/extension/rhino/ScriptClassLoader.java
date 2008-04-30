package org.seasar.chronos.extension.rhino;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;

public class ScriptClassLoader {

	private final List<Script> scriptList = new ArrayList<Script>();

	public void load() {
		final Context context = Context.enter();
		Scriptable global = context.initStandardObjects();
		final Scriptable scope = context.newObject(global);
		scope.setParentScope(null);
		scope.setPrototype(global);
		try {
			ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
			scriptResourceFolder.setPath("./");
			ScriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);
			boolean result = ScriptFileTraversal
					.forEach(new ScriptFileHandler() {
						public void process(String path, InputStream is) {
							InputStreamReader isr = new InputStreamReader(is);
							try {
								Script script = context.compileReader(isr,
										path, 0, null);
								script.exec(context, scope);
								scriptList.add(script);
							} catch (IOException e) {
								e.printStackTrace();
							}

						}
					});
		} finally {
			Context.exit();
		}
	}
}

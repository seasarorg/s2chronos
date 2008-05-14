package org.seasar.chronos.extension.rhino;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.seasar.chronos.core.exception.IllegalAccessRuntimeException;
import org.seasar.chronos.extension.rhino.ScriptFileTraversal.ScriptFileHandler;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.Disposable;

/**
 * Javascriptソースファイルのローダーです．
 * 
 * @author junichi
 * 
 */
public class ScriptLoader implements Disposable {

	private final List<Script> scriptList = new ArrayList<Script>();

	public List<Script> getScriptList() {
		return scriptList;
	}

	public void load() {
		final ScriptContext scriptContext = new ScriptContext();
		final Context context = scriptContext.getContext();
		Scriptable global = context.initStandardObjects();
		final Scriptable scope = context.newObject(global);
		try {
			ScriptableObject.defineClass(scope, S2Container.class);
		} catch (IllegalAccessException e1) {
			throw new IllegalAccessRuntimeException(e1);
		} catch (InstantiationException e1) {

		} catch (InvocationTargetException e1) {

		}
		scope.setParentScope(null);
		scope.setPrototype(global);
		ScriptResourceFolder scriptResourceFolder = new ScriptResourceFolder();
		scriptResourceFolder.setPath("./");
		ScriptFileTraversal.setScriptResourceFolder(scriptResourceFolder);

		ScriptFileTraversal.forEach(new ScriptFileHandler() {
			public void process(String path, InputStream is) {
				InputStreamReader isr = new InputStreamReader(is);
				try {
					Script script = context.compileReader(isr, path, 0, null);
					script.exec(context, scope);
					scriptList.add(script);
				} catch (IOException e) {
					throw new IORuntimeException(e);
				}

			}
		});
	}

	public void unload() {
		scriptList.clear();
	}

	public void dispose() {
		unload();
	}

}

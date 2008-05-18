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
import org.seasar.chronos.core.exception.InstantiationRuntimeException;
import org.seasar.chronos.core.exception.InvocationTargetRuntimeException;
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
		prepareS2Container(context, scope);

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

	private void prepareS2Container(final Context context,
			final Scriptable scope) {
		try {
			ScriptableObject.defineClass(scope, S2Container.class);
		} catch (IllegalAccessException e1) {
			throw new IllegalAccessRuntimeException(e1);
		} catch (InstantiationException e1) {
			throw new InstantiationRuntimeException(e1);
		} catch (InvocationTargetException e1) {
			throw new InvocationTargetRuntimeException(e1);
		}
		Scriptable s2Container = context.newObject(scope, "S2Container", null);
		scope.put("s2Container", scope, s2Container);
	}

	public void unload() {
		scriptList.clear();
	}

	public void dispose() {
		unload();
	}

}

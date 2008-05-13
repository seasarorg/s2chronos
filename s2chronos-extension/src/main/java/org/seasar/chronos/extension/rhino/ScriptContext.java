package org.seasar.chronos.extension.rhino;

import org.mozilla.javascript.Context;

public final class ScriptContext {

	private Context context;

	Context getContext() {
		if (context == null) {
			context = Context.enter();
		}
		return context;
	}

	public static void dispose() {
		Context.exit();
	}
}

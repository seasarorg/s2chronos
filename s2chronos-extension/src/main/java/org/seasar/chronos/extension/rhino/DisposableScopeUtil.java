package org.seasar.chronos.extension.rhino;

import org.seasar.framework.util.Disposable;

public class DisposableScopeUtil {

	public interface ScopeWithDispose extends Disposable {
		public void scope();
	}

	public static void using(ScopeWithDispose scopeWithDispose) {
		try {
			scopeWithDispose.scope();
		} finally {
			try {
				scopeWithDispose.dispose();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	public interface Scope {
		public void scope();
	}

	public static void using(Disposable disposable, Scope scope) {
		try {
			scope.scope();
		} finally {
			if (disposable != null) {
				try {
					disposable.dispose();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}

}

package org.seasar.chronos.core.util;

import org.seasar.framework.util.ResourceUtil;

public class DiconConditionUtil {

	public static boolean hasConvention() {
		return ResourceUtil.getResourceNoException("convention.dicon") != null;
	}

	public static boolean hasCustomizeDicon() {
		return ResourceUtil.getResourceNoException("chronosCustomize.dicon") != null;
	}
}

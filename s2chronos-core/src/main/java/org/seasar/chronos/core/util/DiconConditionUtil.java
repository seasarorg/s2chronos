package org.seasar.chronos.core.util;

import org.seasar.framework.util.ResourceUtil;

public class DiconConditionUtil {

	private static final String CHRONOS_CUSTOMIZE_DICON = "chronosCustomize.dicon";

	private static final String CONVENTION_DICON = "convention.dicon";

	public static boolean hasConvention() {
		return ResourceUtil.getResourceNoException(CONVENTION_DICON) != null;
	}

	public static boolean hasCustomizeDicon() {
		return ResourceUtil.getResourceNoException(CHRONOS_CUSTOMIZE_DICON) != null;
	}
}

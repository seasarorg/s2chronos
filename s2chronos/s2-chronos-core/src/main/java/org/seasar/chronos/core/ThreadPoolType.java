package org.seasar.chronos.core;

public enum ThreadPoolType {

	FIXED, CACHED, SINGLE, SCHEDULED;

	public static ThreadPoolType toEnum(int value) {
		if (value == FIXED.ordinal()) {
			return FIXED;
		} else if (value == CACHED.ordinal()) {
			return CACHED;
		} else if (value == SINGLE.ordinal()) {
			return SINGLE;
		} else if (value == SCHEDULED.ordinal()) {
			return SCHEDULED;
		}
		return null;
	}
}

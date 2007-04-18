package org.seasar.chronos.core.util;

import java.util.concurrent.atomic.AtomicLong;

public final class ObjectUtil {
	private static final AtomicLong objectIdCounter = new AtomicLong();

	/**
	 * アトミックにオブジェクト用のIDを返します．
	 * 
	 * @return
	 */
	public static long generateObjectId() {
		return objectIdCounter.addAndGet(1L);
	}

}

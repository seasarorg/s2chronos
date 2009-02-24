/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.model;

/**
 * @author j5ik2o
 */
public enum ThreadPoolType {
	/**
	 * 固定型
	 */
	FIXED,
	/**
	 * キャッシュ型
	 */
	CACHED,
	/**
	 * シングル型
	 */
	SINGLE,
	/**
	 * スケジュール型
	 */
	SCHEDULED;
	/**
	 * 整数型を列挙型に変換します．
	 * 
	 * @param value
	 * @return {@link ThreadPoolType}
	 */
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

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
package org.seasar.chronos.core.annotation.task;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * タスク用アノテーションです。
 * 
 * @author j5ik2o
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Task {
	/**
	 * タスク名用NULL文字列定数です。
	 */
	public static final String TASK_NAME_NULL = "";

	/**
	 * タスク名です。
	 * 
	 * @return タスク名
	 */
	String name() default TASK_NAME_NULL;

	/**
	 * 自動スケジュールです。
	 * 
	 * @return 自動スケジュールするならtrue、しないならfalse
	 */
	boolean autoSchedule() default true;

	/**
	 * クローンタスクです。
	 * 
	 * @return クローンするタスク数
	 */
	long cloneTask() default 1L;
}

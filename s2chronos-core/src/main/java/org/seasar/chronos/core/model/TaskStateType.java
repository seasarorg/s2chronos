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
 * タスク状態です．
 * 
 * @author j5ik2o
 */
public enum TaskStateType {
	/**
	 * スケジュールに登録された状態
	 */
	SCHEDULED,
	/**
	 * 実行中の状態
	 */
	RUNNING,
	/**
	 * キャンセル中の状態
	 */
	CANCELING,
	/**
	 * スケジュールから除外された状態
	 */
	UNSCHEDULED;
}

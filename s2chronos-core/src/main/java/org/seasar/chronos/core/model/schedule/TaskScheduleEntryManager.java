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
package org.seasar.chronos.core.model.schedule;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.seasar.chronos.core.model.TaskScheduleEntry;
import org.seasar.chronos.core.model.TaskStateType;
import org.seasar.framework.log.Logger;

/**
 * タスクのスケジュールエントリを管理するクラスです。
 * 
 * @author j5ik2o
 */
public class TaskScheduleEntryManager {
	/**
	 * タスクのスケジュール情報を読むためのハンドラーです。
	 * 
	 * @author j5ik2o
	 */
	public interface TaskScheduleEntryHanlder<T> {
		/**
		 * ハンドラーです。
		 * 
		 * @param scheduleEntry
		 *            タスクのスケジュール情報
		 * @return オブジェクト。null以外を返すと呼び出し元のプロセスが終了する。
		 */
		public T processTaskScheduleEntry(TaskScheduleEntry scheduleEntry);
	}

	@SuppressWarnings("unused")
	private static Logger log =
	    Logger.getLogger(TaskScheduleEntryManager.class);

	/**
	 * シングルトンな{@link TaskScheduleEntryManager}のインスタンスです。
	 */
	private static TaskScheduleEntryManager instance;

	/**
	 * シングルトンインスタンスを返します．
	 * 
	 * @return {@link TaskScheduleEntryManager}
	 */
	public static TaskScheduleEntryManager getInstance() {
		if (instance == null) {
			synchronized (TaskScheduleEntryManager.class) {
				if (instance == null) {
					instance = new TaskScheduleEntryManager();
				}
			}
		}
		return instance;
	}

	private final Map<TaskStateType, CopyOnWriteArrayList<TaskScheduleEntry>> taskScheduleEntryMap =
	    new ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskScheduleEntry>>();

	private final CopyOnWriteArrayList<TaskScheduleEntry> allTaskList =
	    new CopyOnWriteArrayList<TaskScheduleEntry>();

	/**
	 * コンポーネントクラスからタスクスケジュールエントリを検索するための{@link Map}です．
	 */
	private final Map<Class<?>, TaskScheduleEntry> taskScheduleEntryClassMap =
	    new ConcurrentHashMap<Class<?>, TaskScheduleEntry>();

	/**
	 * コンストラクタです．
	 */
	private TaskScheduleEntryManager() {
	}

	/**
	 * タスクスケジュールエントリを追加します．
	 * 
	 * @param key
	 *            {@link TaskStateType}
	 * @param taskScheduleEntry
	 *            {@link TaskScheduleEntry}
	 * @return 追加成功 = true, 追加失敗 = false
	 */
	public boolean addTaskScheduleEntry(TaskStateType key,
	        TaskScheduleEntry taskScheduleEntry) {
		taskScheduleEntry.setTaskStateType(key);
		boolean result = this.getScheduleEntryList(key).add(taskScheduleEntry);
		if (key == TaskStateType.SCHEDULED) {
			result = result && this.allTaskList.addIfAbsent(taskScheduleEntry);
			Class<?> taskComponentClass = taskScheduleEntry.getTaskClass();
			if (!this.taskScheduleEntryClassMap.containsKey(taskComponentClass)) {
				this.taskScheduleEntryClassMap.put(
				    taskComponentClass,
				    taskScheduleEntry);
			} else {
				result = false;
			}
		} else if (key == TaskStateType.UNSCHEDULED) {
			taskScheduleEntry.setUnScheduledDate(Calendar
			    .getInstance()
			    .getTime());
		}
		return result;
	}

	/**
	 * 指定したタスク状態のタスクスケジュールエントリをすべて削除します．
	 * 
	 * @param key
	 *            {@link TaskStateType}
	 */
	public void allRemove(TaskStateType key) {
		this.getScheduleEntryList(key).clear();
	}

	/**
	 * 指定したキーのタスクスケジュールエントリが存在するかどうかを返します．
	 * 
	 * @param key
	 *            {@link TaskScheduleEntry}もしくはコンポーネントクラス
	 * @return 存在する場合はtrue, しない場合はfalse
	 */
	public boolean contains(Object key) {
		// TODO メソッドを分割したほうがよい
		if (key instanceof TaskScheduleEntry) {
			return this.allTaskList.contains(key);
		}
		return this.taskScheduleEntryClassMap.containsKey(key);
	}

	/**
	 * 指定したタスク状態にタスクスケジュールエントリが含まれるかどうかを返します．
	 * 
	 * @param key
	 *            {@link TaskStateType}
	 * @param taskScheduleEntry
	 *            {@link TaskScheduleEntry}
	 * @return 存在する場合はtrue, しない場合はfalse
	 */
	public boolean contains(TaskStateType key,
	        TaskScheduleEntry taskScheduleEntry) {
		// TODO メソッドを分割したほうがよい
		CopyOnWriteArrayList<TaskScheduleEntry> result =
		    taskScheduleEntryMap.get(key);
		if (result != null) {
			return result.contains(taskScheduleEntry);
		}
		return false;
	}

	/**
	 * タスクスケジュールエントリをハンドラを使って順番に処理します．
	 * 
	 * @param <T>
	 *            ハンダラの戻り値の型
	 * @param handler
	 *            　{@link TaskScheduleEntryHanlder}
	 * @return ハンドラからの戻り値
	 */
	public <T> T forEach(TaskScheduleEntryHanlder<T> handler) {
		for (TaskScheduleEntry tse : allTaskList) {
			T result = handler.processTaskScheduleEntry(tse);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * 　指定したタスク状態の，タスクスケジュールエントリをハンドラを使って順番に処理します．
	 * 
	 * @param <T>
	 *            ハンダラの戻り値の型
	 * @param key
	 *            {@link TaskStateType}
	 * @param handler
	 *            {@link TaskScheduleEntryHanlder}
	 * @return ハンドラからの戻り値
	 */
	public <T> T forEach(TaskStateType key, TaskScheduleEntryHanlder<T> handler) {
		CopyOnWriteArrayList<TaskScheduleEntry> scheduleEntryList =
		    getScheduleEntryList(key);
		for (TaskScheduleEntry tse : scheduleEntryList) {
			T result = handler.processTaskScheduleEntry(tse);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	private CopyOnWriteArrayList<TaskScheduleEntry> getScheduleEntryList(
	        TaskStateType key) {
		CopyOnWriteArrayList<TaskScheduleEntry> result =
		    this.taskScheduleEntryMap.get(key);
		if (result == null) {
			result = new CopyOnWriteArrayList<TaskScheduleEntry>();
			this.taskScheduleEntryMap.put(key, result);
		}
		return result;
	}

	/**
	 * タスクスケジュールエントリを返します．
	 * 
	 * @param componentClass
	 *            コンポーネントキー
	 * @return {@link TaskScheduleEntry}
	 */
	public TaskScheduleEntry getTaskScheduleEntry(Class<?> componentClass) {
		return this.taskScheduleEntryClassMap.get(componentClass);
	}

	/**
	 * @param taskScheduleEntry
	 * @return
	 */
	public boolean removeTaskScheduleEntry(TaskScheduleEntry taskScheduleEntry) {
		boolean result = allTaskList.remove(taskScheduleEntry);
		if (result) {
			for (TaskStateType key : taskScheduleEntryMap.keySet()) {
				CopyOnWriteArrayList<TaskScheduleEntry> taskScheduleEntryList =
				    taskScheduleEntryMap.get(key);
				if (taskScheduleEntryList != null) {
					result = taskScheduleEntryList.remove(taskScheduleEntry);
				}
			}
			Class<?> taskComponentClass = taskScheduleEntry.getTaskClass();
			if (taskComponentClass != null) {
				taskScheduleEntryClassMap.remove(taskComponentClass);
			}
		}
		return result;
	}

	/**
	 * @param key
	 * @param taskScheduleEntry
	 * @return
	 */
	public boolean removeTaskScheduleEntry(TaskStateType key,
	        TaskScheduleEntry taskScheduleEntry) {
		boolean result = false;
		result = this.getScheduleEntryList(key).remove(taskScheduleEntry);
		if (key == TaskStateType.UNSCHEDULED) {
			result = result & this.allTaskList.remove(taskScheduleEntry);
			result =
			    result
			        & this.taskScheduleEntryClassMap.remove(taskScheduleEntry
			            .getComponentDef()
			            .getComponentClass()) != null;
		}
		taskScheduleEntry = null;
		return result;
	}

	/**
	 * タスクスケジュールエントリのサイズを返します．
	 * 
	 * @return タスクスケジュールエントリのサイズ
	 */
	public int size() {
		return this.allTaskList.size();
	}

	/**
	 * 指定されたタスク状態のタスクスケジュールエントリのサイズを返します．
	 * 
	 * @param key
	 *            {@link TaskStateType}
	 * @return 指定されたタスク状態のタスクスケジュールエントリのサイズ
	 */
	public int size(TaskStateType key) {
		List<?> list = taskScheduleEntryMap.get(key);
		if (list == null) {
			return 0;
		}
		return list.size();
	}
}

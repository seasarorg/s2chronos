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
 * タスクのスケジュール情報を管理するクラスです。
 * 
 * @author j5ik2o
 */
public class TaskScheduleEntryManager {
	/**
	 * タスクのスケジュール情報を読むためのハンドラーです。
	 * 
	 * @author j5ik2o
	 */
	public interface TaskScheduleEntryHanlder {
		/**
		 * ハンドラーです。
		 * 
		 * @param scheduleEntry
		 *            タスクのスケジュール情報
		 * @return オブジェクト。null以外を返すと呼び出し元のプロセスが終了する。
		 */
		public Object processTaskScheduleEntry(TaskScheduleEntry scheduleEntry);
	}

	@SuppressWarnings("unused")
	private static Logger log =
		Logger.getLogger(TaskScheduleEntryManager.class);

	/**
	 * シングルトンな{@link TaskScheduleEntryManager}のインスタンスです。
	 */
	private static TaskScheduleEntryManager instance;

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

	private final Map<Class<?>, TaskScheduleEntry> taskScheduleEntryClassMap =
		new ConcurrentHashMap<Class<?>, TaskScheduleEntry>();

	private TaskScheduleEntryManager() {
	}

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

	public void allRemove(TaskStateType key) {
		this.getScheduleEntryList(key).clear();
	}

	public boolean contains(Object key) {
		if (key instanceof TaskScheduleEntryImpl) {
			return this.allTaskList.contains(key);
		}
		return this.taskScheduleEntryClassMap.containsKey(key);
	}

	public boolean contains(TaskStateType key, TaskScheduleEntry taskContena) {
		CopyOnWriteArrayList<TaskScheduleEntry> result =
			taskScheduleEntryMap.get(key);
		if (result != null) {
			return result.contains(taskContena);
		}
		return false;
	}

	public Object forEach(TaskScheduleEntryHanlder handler) {
		for (TaskScheduleEntry tse : allTaskList) {
			Object result = handler.processTaskScheduleEntry(tse);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public Object forEach(TaskStateType key, TaskScheduleEntryHanlder handler) {
		CopyOnWriteArrayList<TaskScheduleEntry> scheduleEntryList =
			getScheduleEntryList(key);
		for (TaskScheduleEntry tse : scheduleEntryList) {
			Object result = handler.processTaskScheduleEntry(tse);
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

	public TaskScheduleEntry getTaskScheduleEntry(Object key) {
		return this.taskScheduleEntryClassMap.get(key);
	}

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

	public int size() {
		return this.allTaskList.size();
	}

	public int size(TaskStateType key) {
		List<?> list = taskScheduleEntryMap.get(key);
		if (list == null) {
			return 0;
		}
		return list.size();
	}
}

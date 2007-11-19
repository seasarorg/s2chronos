package org.seasar.chronos.core.schedule;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.framework.log.Logger;

public class TaskScheduleEntryManager {

	public interface TaskScheduleEntryHanlder {
		public Object processTaskScheduleEntry(TaskScheduleEntry scheduleEntry);
	}

	@SuppressWarnings("unused")
	private static Logger log = Logger
			.getLogger(TaskScheduleEntryManager.class);

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

	private ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskScheduleEntry>> taskScheduleEntryMap = new ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskScheduleEntry>>();

	private CopyOnWriteArrayList<TaskScheduleEntry> allTaskList = new CopyOnWriteArrayList<TaskScheduleEntry>();

	private ConcurrentHashMap<Class<?>, TaskScheduleEntry> taskScheduleEntryClassMap = new ConcurrentHashMap<Class<?>, TaskScheduleEntry>();

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
				this.taskScheduleEntryClassMap.put(taskComponentClass,
						taskScheduleEntry);
			} else {
				result = false;
			}
		}
		return result;
	}

	public void allRemove(TaskStateType key) {
		this.getScheduleEntryList(key).clear();
	}

	public boolean contains(Object key) {
		if (key instanceof ScheduleEntry) {
			return this.allTaskList.contains((TaskScheduleEntry) key);
		}
		return this.taskScheduleEntryClassMap.containsKey(key);
	}

	public boolean contains(TaskStateType key, TaskScheduleEntry taskContena) {
		return taskScheduleEntryMap.get(key).contains(taskContena);
	}

	public Object forEach(TaskScheduleEntryHanlder handler) {
		for (TaskScheduleEntry tc : allTaskList) {
			Object result = handler.processTaskScheduleEntry(tc);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public Object forEach(TaskStateType key, TaskScheduleEntryHanlder handler) {
		CopyOnWriteArrayList<TaskScheduleEntry> scheduleEntryList = getScheduleEntryList(key);
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
		CopyOnWriteArrayList<TaskScheduleEntry> result = this.taskScheduleEntryMap
				.get(key);
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
				result = taskScheduleEntryMap.get(key)
						.remove(taskScheduleEntry);
			}
			Class<?> taskComponentClass = taskScheduleEntry.getTaskClass();
			taskScheduleEntryClassMap.remove(taskComponentClass);
		}
		return result;
	}

	public boolean removeTaskScheduleEntry(TaskStateType key,
			TaskScheduleEntry taskScheduleEntry) {
		boolean result = false;
		result = this.getScheduleEntryList(key).remove(taskScheduleEntry);
		if (key == TaskStateType.UNSCHEDULED) {
			result = result & this.allTaskList.remove(taskScheduleEntry);
			result = result
					& this.taskScheduleEntryClassMap.remove(taskScheduleEntry
							.getComponentDef().getComponentClass()) != null;
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

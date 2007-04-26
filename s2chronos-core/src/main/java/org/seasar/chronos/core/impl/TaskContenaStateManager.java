package org.seasar.chronos.core.impl;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.seasar.chronos.core.logger.Logger;
import org.seasar.chronos.core.util.TaskPropertyUtil;

public class TaskContenaStateManager {

	public interface TaskContenaHanlder {
		public Object processTaskContena(TaskContena taskContena);
	}

	private static Logger log = Logger.getLogger(TaskContenaStateManager.class);

	private static TaskContenaStateManager instance;

	public static TaskContenaStateManager getInstance() {
		if (instance == null) {
			synchronized (TaskContenaStateManager.class) {
				if (instance == null) {
					instance = new TaskContenaStateManager();
				}
			}
		}
		return instance;
	}

	private ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>>();

	private CopyOnWriteArrayList<TaskContena> allTaskList = new CopyOnWriteArrayList<TaskContena>();

	private ConcurrentHashMap<Object, TaskContena> taskContenaObjectMap = new ConcurrentHashMap<Object, TaskContena>();

	private TaskContenaStateManager() {

	}

	public boolean addTaskContena(TaskStateType key, TaskContena taskContena) {
		log.debug("addTaskContena : key = "
				+ key
				+ " value = "
				+ TaskPropertyUtil.getTaskName(taskContena
						.getTaskExecutorService()) + " class = "
				+ taskContena.getClass().getName());
		boolean result = this.getTaskContenaList(key).add(taskContena);
		if (key == TaskStateType.SCHEDULED) {
			result = result & this.allTaskList.addIfAbsent(taskContena);
			// オブジェクトでput
			result = result
					& this.taskContenaObjectMap.putIfAbsent(taskContena
							.getTask(), taskContena) != null;
			// タスク名でput
			result = result
					& this.taskContenaObjectMap.putIfAbsent(TaskPropertyUtil
							.getTaskName(taskContena.getTaskExecutorService()),
							taskContena) != null;
		}
		return result;
	}

	public void allRemove(TaskStateType key) {
		this.getTaskContenaList(key).clear();
	}

	public boolean contains(Object key) {
		if (key instanceof TaskContena) {
			return this.allTaskList.contains((TaskContena) key);
		}
		return this.taskContenaObjectMap.containsKey(key);
	}

	public boolean contains(TaskStateType key, TaskContena taskContena) {
		return taskContenaMap.get(key).contains(taskContena);
	}

	public Object forEach(TaskContenaHanlder handler) {
		for (TaskContena tc : allTaskList) {
			Object result = handler.processTaskContena(tc);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public Object forEach(TaskStateType key, TaskContenaHanlder handler) {
		CopyOnWriteArrayList<TaskContena> taskContenaList = getTaskContenaList(key);
		for (TaskContena tc : taskContenaList) {
			Object result = handler.processTaskContena(tc);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	public TaskContena getTaskContena(Object key) {
		return this.taskContenaObjectMap.get(key);
	}

	private CopyOnWriteArrayList<TaskContena> getTaskContenaList(
			TaskStateType key) {
		CopyOnWriteArrayList<TaskContena> taskContenaList = this.taskContenaMap
				.get(key);
		if (taskContenaList == null) {
			taskContenaList = new CopyOnWriteArrayList<TaskContena>();
			this.taskContenaMap.put(key, taskContenaList);
		}
		return taskContenaList;
	}

	public boolean removeTaskContena(TaskStateType key, TaskContena taskContena) {
		boolean result = false;
		result = this.getTaskContenaList(key).remove(taskContena);
		if (key == TaskStateType.UNSCHEDULED) {
			result = result & this.allTaskList.remove(taskContena);
			result = result
					& this.taskContenaObjectMap.remove(taskContena.getTask()) != null;
			result = result
					& this.taskContenaObjectMap.remove(TaskPropertyUtil
							.getTaskName(taskContena.getTaskExecutorService())) != null;
		}
		return result;
	}

	public int size() {
		return this.allTaskList.size();
	}

	public int size(TaskStateType key) {
		List list = taskContenaMap.get(key);
		if (list == null) {
			return 0;
		}
		return list.size();
	}

}

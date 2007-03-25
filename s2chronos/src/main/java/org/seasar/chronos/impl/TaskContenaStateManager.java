package org.seasar.chronos.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskContenaStateManager {

	private static TaskContenaStateManager instance;

	private ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>>();

	private TaskContenaStateManager() {

	}

	public CopyOnWriteArrayList<TaskContena> getAllTaskContenaList() {
		CopyOnWriteArrayList<TaskContena> result = new CopyOnWriteArrayList<TaskContena>();
		Set<TaskStateType> set = this.taskContenaMap.keySet();
		for (TaskStateType type : set) {
			CopyOnWriteArrayList<TaskContena> list = this.taskContenaMap
					.get(type);
			result.addAllAbsent(list);
		}
		return result;
	}

	public CopyOnWriteArrayList<TaskContena> getTaskContenaList(
			TaskStateType key) {
		CopyOnWriteArrayList<TaskContena> result = this.taskContenaMap.get(key);
		if (result == null) {
			result = new CopyOnWriteArrayList<TaskContena>();
			this.putTaskContenaList(key, result);
		}
		return result;
	}

	private void putTaskContenaList(TaskStateType key,
			CopyOnWriteArrayList<TaskContena> taskContenaList) {
		this.taskContenaMap.put(key, taskContenaList);
	}

	public static TaskContenaStateManager getInstance() {
		if (instance == null) {
			instance = new TaskContenaStateManager();
		}
		return instance;
	}

}

package org.seasar.chronos.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskContenaStateManager {

	private static TaskContenaStateManager instance;

	private ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>> taskContenaMap = new ConcurrentHashMap<TaskStateType, CopyOnWriteArrayList<TaskContena>>();

	private CopyOnWriteArrayList<TaskContena> allTaskList = new CopyOnWriteArrayList<TaskContena>();

	private TaskContenaStateManager() {

	}

	public CopyOnWriteArrayList<TaskContena> getAllTaskContenaList() {
		return allTaskList;
	}

	public CopyOnWriteArrayList<TaskContena> getTaskContenaList(
			TaskStateType key) {
		CopyOnWriteArrayList<TaskContena> result = this.taskContenaMap.get(key);
		if (result == null) {
			result = new CopyOnWriteArrayList<TaskContena>();
			this.putTaskContenaList(key, result);
		}
		CopyOnWriteArrayList<TaskContena> clone = (CopyOnWriteArrayList<TaskContena>) result.clone();
		return clone;
	}

	private void putTaskContenaList(TaskStateType key,
			CopyOnWriteArrayList<TaskContena> taskContenaList) {
		this.taskContenaMap.put(key, taskContenaList);
	}

	public void addTaskContena(TaskStateType key, TaskContena taskContena) {
		getTaskContenaList(key).add(taskContena);
		allTaskList.add(taskContena);
	}

	public static TaskContenaStateManager getInstance() {
		if (instance == null) {
			instance = new TaskContenaStateManager();
		}
		return instance;
	}

}

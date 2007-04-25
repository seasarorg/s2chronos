package org.seasar.chronos.extension.store.impl;

import java.util.List;

import org.seasar.chronos.core.impl.TaskContena;
import org.seasar.chronos.core.impl.TaskContenaStateManager;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.impl.TaskContenaStateManager.TaskContenaHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.extension.store.ScheduleStore;
import org.seasar.chronos.extension.store.dao.ScheduleDao;
import org.seasar.chronos.extension.store.entity.ScheduleEntity;

public class ScheduleStoreImpl implements ScheduleStore {

	private ScheduleDao scheduleDao;

	private TaskContenaStateManager taskContenaStateManager = TaskContenaStateManager
			.getInstance();

	public void loadAllTasks() {
		List<ScheduleEntity> entities = scheduleDao.selectAll();
		for (ScheduleEntity entity : entities) {
			TaskContena taskContena = taskContenaStateManager
					.getTaskContena(entity.getTaskName());
			TaskExecutorService tes = taskContena.getTaskExecutorService();
			tes.setTaskId(entity.getTaskId());
			tes.load();
			this.taskContenaStateManager.addTaskContena(
					TaskStateType.SCHEDULED, taskContena);
		}
	}

	public void saveAllTasks() {
		taskContenaStateManager.forEach(TaskStateType.SCHEDULED,
				new TaskContenaHanlder() {
					public Object processTaskContena(TaskContena taskContena) {
						TaskExecutorService tes = taskContena
								.getTaskExecutorService();
						tes.save();
						return null;
					}
				});
	}

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

}

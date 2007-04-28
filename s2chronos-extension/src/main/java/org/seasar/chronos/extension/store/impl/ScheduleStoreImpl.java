package org.seasar.chronos.extension.store.impl;

import java.math.BigDecimal;
import java.util.List;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager;
import org.seasar.chronos.core.schedule.TaskScheduleEntryManager.TaskScheduleEntryHanlder;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.chronos.extension.store.ScheduleStore;
import org.seasar.chronos.extension.store.dao.ScheduleDao;
import org.seasar.chronos.extension.store.dxo.ScheduleDxo;
import org.seasar.chronos.extension.store.entity.ScheduleEntity;

public class ScheduleStoreImpl implements ScheduleStore {

	private ScheduleDao scheduleDao;

	private ScheduleDxo scheduleDxo;

	private TaskScheduleEntryManager taskContenaStateManager = TaskScheduleEntryManager
			.getInstance();

	public void loadAllTasks() {
		List<ScheduleEntity> entities = scheduleDao.selectAll();
		for (ScheduleEntity entity : entities) {
			TaskScheduleEntry taskContena = taskContenaStateManager
					.getTaskScheduleEntry(entity.getTaskName());
			TaskExecutorService tes = taskContena.getTaskExecutorService();
			tes.setTaskId(entity.getTaskId());
			tes.load();
			this.taskContenaStateManager.addTaskScheduleEntry(
					TaskStateType.SCHEDULED, taskContena);
		}
	}

	public void loadFromStore(Long id, TaskScheduleEntry taskScheduleEntry) {
		taskScheduleEntry.getTaskExecutorService().load();

	}

	public void loadFromStoreByObjectId(Long id,
			TaskScheduleEntry taskScheduleEntry) {
		taskScheduleEntry.getTaskExecutorService().load();
		ScheduleEntity scheduleEntity = this.scheduleDao.selectByObjectId(id);
		if (scheduleEntity == null) {
			return;
		}
		scheduleDxo.fromEntityToComponent(scheduleEntity, taskScheduleEntry);
	}

	public void saveAllTasks() {
		taskContenaStateManager.forEach(TaskStateType.SCHEDULED,
				new TaskScheduleEntryHanlder() {
					public Object processTaskScheduleEntry(
							TaskScheduleEntry taskContena) {
						TaskExecutorService tes = taskContena
								.getTaskExecutorService();
						tes.save();
						return null;
					}
				});
	}

	public Long saveToStore(TaskScheduleEntry taskScheduleEntry) {
		boolean update = true;
		ScheduleEntity entity = this.scheduleDao
				.selectByObjectId(taskScheduleEntry.getScheduleId());
		if (entity == null) {
			entity = this.scheduleDxo.toEntity(taskScheduleEntry);
			update = false;
		} else {
			this.scheduleDxo.fromComponentToEntity(taskScheduleEntry, entity);
		}
		taskScheduleEntry.getTaskExecutorService().save();
		if (update) {
			this.scheduleDao.update(entity);
		} else {
			entity.setVersionNo(new BigDecimal(1L));
			this.scheduleDao.insert(entity);
		}
		return null;
	}

	public void setScheduleDao(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}

	public void setScheduleDxo(ScheduleDxo scheduleDxo) {
		this.scheduleDxo = scheduleDxo;
	}

}

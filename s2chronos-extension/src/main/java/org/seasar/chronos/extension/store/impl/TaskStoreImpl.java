package org.seasar.chronos.extension.store.impl;

import java.math.BigDecimal;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.chronos.extension.store.TaskStore;
import org.seasar.chronos.extension.store.ThreadPoolStore;
import org.seasar.chronos.extension.store.TriggerStore;
import org.seasar.chronos.extension.store.dao.TaskDao;
import org.seasar.chronos.extension.store.dxo.TaskDxo;
import org.seasar.chronos.extension.store.entity.TaskEntity;

public class TaskStoreImpl implements TaskStore {

	private TaskDao taskDao;

	private TaskDxo taskDxo;

	private TriggerStore triggerStoreImpl;

	private ThreadPoolStore threadPoolStoreImpl;

	public void loadFromStoreByObjectId(Long objectId, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectByObjectId(objectId);
		if (entity == null) {
			return;
		}
		fromEntityToComponent(task, entity);
	}

	public void loadFromStore(Long id, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectById(id);
		if (entity == null) {
			return;
		}
		fromEntityToComponent(task, entity);
	}

	private void fromEntityToComponent(TaskProperties task, TaskEntity entity) {
		Long triggerId = entity.getTriggerId();
		if (triggerId != null) {
			TaskTrigger taskTrigger = triggerStoreImpl.loadFromStore(triggerId);
			task.setTrigger(taskTrigger);
			TaskThreadPool taskThreadPool = this.threadPoolStoreImpl
					.loadFromStore(taskTrigger.getTriggerId());
			task.setThreadPool(taskThreadPool);
		}
		this.taskDxo.fromEntityToComponent(entity, task);
	}

	public Long saveToStore(TaskProperties task) {
		boolean update = true;
		TaskEntity entity = this.taskDao.selectByObjectId(task.getTaskId());
		if (entity == null) {
			entity = this.taskDxo.toEntity(task);
			update = false;
		} else {
			this.taskDxo.fromComponentToEntity(task, entity);
		}
		TaskTrigger taskTrigger = task.getTrigger();
		TaskThreadPool taskThreadPool = task.getThreadPool();
		if (taskTrigger != null
				&& null == triggerStoreImpl
						.loadFromStore(entity.getTriggerId())) {
			triggerStoreImpl.saveToStore(taskTrigger);
		}
		if (taskThreadPool != null
				&& null == threadPoolStoreImpl.loadFromStore(entity
						.getThreadPoolId())) {
			threadPoolStoreImpl.saveToStore(taskThreadPool);
		}

		if (update) {
			this.taskDao.update(entity);
		} else {
			entity.setVersionNo(new BigDecimal(1L));
			this.taskDao.insert(entity);
		}

		return entity.getId();

	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setTaskDxo(TaskDxo taskDxo) {
		this.taskDxo = taskDxo;
	}

	public void setThreadPoolStore(ThreadPoolStore threadPoolStoreImpl) {
		this.threadPoolStoreImpl = threadPoolStoreImpl;
	}

	public void setTriggerStore(TriggerStore triggerStoreImpl) {
		this.triggerStoreImpl = triggerStoreImpl;
	}

}

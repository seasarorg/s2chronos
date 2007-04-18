package org.seasar.chronos.extension.store;

import java.math.BigDecimal;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.task.TaskProperties;
import org.seasar.chronos.extension.store.dao.TaskDao;
import org.seasar.chronos.extension.store.dxo.TaskDxo;
import org.seasar.chronos.extension.store.entity.TaskEntity;

public class TaskStoreImpl implements TaskStore {

	private TaskDao taskDao;

	private TaskDxo taskDxo;

	private TriggerStore triggerStore;

	private ThreadPoolStore threadPoolStore;

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
			TaskTrigger taskTrigger = triggerStore.loadFromStore(triggerId);
			task.setTrigger(taskTrigger);
			TaskThreadPool taskThreadPool = this.threadPoolStore
					.loadFromStore(taskTrigger.getId());
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
		}
		TaskTrigger taskTrigger = task.getTrigger();
		TaskThreadPool taskThreadPool = task.getThreadPool();
		if (taskTrigger != null
				&& null == triggerStore.loadFromStore(entity.getTriggerId())) {
			triggerStore.saveToStore(taskTrigger);
		}
		if (taskThreadPool != null
				&& null == threadPoolStore.loadFromStore(entity
						.getThreadPoolId())) {
			threadPoolStore.saveToStore(taskThreadPool);
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

	public void setThreadPoolStore(ThreadPoolStore threadPoolStore) {
		this.threadPoolStore = threadPoolStore;
	}

	public void setTriggerStore(TriggerStore triggerStore) {
		this.triggerStore = triggerStore;
	}

}

package org.seasar.chronos.extension.store;

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

	public void loadFromStore(Integer code, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectByTaskCodeNewest(code);
		if (entity == null) {
			return;
		}
		TaskTrigger taskTrigger = triggerStore.loadFromStore(entity
				.getTriggerId());
		task.setTrigger(taskTrigger);
		TaskThreadPool taskThreadPool = this.threadPoolStore
				.loadFromStore(taskTrigger.getId());
		task.setThreadPool(taskThreadPool);
		this.taskDxo.fromEntityToComponent(entity, task);
	}

	public void loadFromStore(Long id, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectById(id);
		if (entity == null) {
			return;
		}
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

		TaskEntity entity = this.taskDxo.toEntity(task);
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
		if (entity.getId() == null) {
			this.taskDao.insert(entity);
		} else {
			this.taskDao.update(entity);
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

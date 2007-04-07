package org.seasar.chronos.store;

import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.store.dao.TaskDao;
import org.seasar.chronos.store.dxo.TaskDxo;
import org.seasar.chronos.store.entity.TaskEntity;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.framework.exception.SQLRuntimeException;

public class TaskStore {

	private TaskDao taskDao;

	private TaskDxo taskDxo;

	private TriggerStore triggerStore;

	private ThreadPoolStore threadPoolStore;

	public void loadFromStore(int id, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectById(id);
		TaskTrigger taskTrigger = triggerStore.loadFromStore(entity
				.getTriggerId());
		task.setTrigger(taskTrigger);
		TaskThreadPool taskThreadPool = this.threadPoolStore.loadFromStore(id);
		task.setThreadPool(taskThreadPool);
		this.taskDxo.fromEntityFromComponent(entity, task);
	}

	public void saveToStore(TaskProperties task) {
		TaskEntity entity = this.taskDxo.toEntity(task);
		TaskTrigger taskTrigger = task.getTrigger();
		TaskThreadPool taskThreadPool = task.getThreadPool();
		// ‚È‚¯‚ê‚ÎDB‚É•Û‘¶
		if (null == triggerStore.loadFromStore(entity.getTriggerId())) {
			triggerStore.saveToStore(taskTrigger);
		}
		if (null == threadPoolStore.loadFromStore(entity.getThreadPoolId())) {
			threadPoolStore.saveToStore(taskThreadPool);
		}
		try {
			this.taskDao.update(entity);
		} catch (SQLRuntimeException ex) {
			this.taskDao.insert(entity);
		}
	}

	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setTaskDxo(TaskDxo taskDxo) {
		this.taskDxo = taskDxo;
	}

	public void setTriggerStore(TriggerStore triggerStore) {
		this.triggerStore = triggerStore;
	}

}

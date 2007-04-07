package org.seasar.chronos.store.task;

import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.store.dao.TaskDao;
import org.seasar.chronos.store.dxo.TaskDxo;
import org.seasar.chronos.store.entity.TaskEntity;
import org.seasar.chronos.store.trigger.TriggerStore;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.framework.exception.SQLRuntimeException;

public class TaskStore {

	private TaskDao taskDao;

	private TaskDxo taskDxo;

	private TriggerStore triggerStore;

	public void loadFromStore(int id, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectById(id);
		TaskTrigger taskTrigger = triggerStore.loadFromStore(entity
				.getTriggerId());
		task.setTrigger(taskTrigger);
		this.taskDxo.fromEntityFromComponent(entity, task);
	}

	public void saveToStore(TaskProperties task) {
		TaskEntity entity = this.taskDxo.toEntity(task);
		TaskTrigger taskTrigger = task.getTrigger();
		entity.setTriggerId(taskTrigger.getId());
		// ‚È‚¯‚ê‚ÎDB‚É•Û‘¶
		if (null == triggerStore.loadFromStore(taskTrigger.getId())) {
			triggerStore.saveToStore(taskTrigger);
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

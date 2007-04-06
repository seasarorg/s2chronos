package org.seasar.chronos.store.task;

import org.seasar.chronos.store.dao.TaskDao;
import org.seasar.chronos.store.dxo.TaskDxo;
import org.seasar.chronos.store.entity.TaskEntity;
import org.seasar.chronos.task.TaskProperties;
import org.seasar.framework.exception.SQLRuntimeException;

public class TaskStore extends AbstractTaskStore {

	private TaskDao taskDao;

	private TaskDxo taskDxo;

	public void loadFromStore(long id, TaskProperties task) {
		TaskEntity entity = this.taskDao.selectById(id);
		this.taskDxo.fromEntityFromComponent(entity, task);
	}

	public void saveToStore(TaskProperties task) {
		TaskEntity entity = this.taskDxo.toEntity(task);
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

}

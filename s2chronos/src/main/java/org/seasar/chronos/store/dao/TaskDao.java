package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.TaskEntity;

public interface TaskDao {

	public int insert(TaskEntity entity);

	public int update(TaskEntity entity);

	public int delete(TaskEntity entity);

	public TaskEntity selectById(Long id);

	public List<TaskEntity> selectAll();

}

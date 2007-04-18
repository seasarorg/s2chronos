package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.TaskEntity;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TaskEntity.class)
public interface TaskDao {

	public int insert(TaskEntity entity);

	public int update(TaskEntity entity);

	public int delete(TaskEntity entity);

	@Arguments("ID")
	public TaskEntity selectById(Long id);

	@Arguments("OBJECT_ID")
	public TaskEntity selectByObjectId(Long objectId);

	public List<TaskEntity> selectAll();

}

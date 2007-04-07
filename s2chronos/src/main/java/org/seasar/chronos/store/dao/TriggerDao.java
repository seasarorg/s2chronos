package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.TriggerEntity;

public interface TriggerDao {

	public int insert(TriggerEntity entity);

	public int update(TriggerEntity entity);

	public int delete(TriggerEntity entity);

	public TriggerEntity selectById(int id);

	public List<TriggerEntity> selectAll();

}

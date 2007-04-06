package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.TriggerLogEntity;

public interface TriggerLogDao {

	public int insert(TriggerLogEntity entity);

	public int update(TriggerLogEntity entity);

	public int delete(TriggerLogEntity entity);

	public TriggerLogEntity selectById(Long id);

	public List<TriggerLogEntity> selectAll();

}

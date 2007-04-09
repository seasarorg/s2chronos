package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.TriggerLogEntity;

public interface TriggerLogDao {

	public int insert(TriggerLogEntity entity);

	public int update(TriggerLogEntity entity);

	public int delete(TriggerLogEntity entity);

	public TriggerLogEntity selectById(int id);

	public List<TriggerLogEntity> selectAll();

}
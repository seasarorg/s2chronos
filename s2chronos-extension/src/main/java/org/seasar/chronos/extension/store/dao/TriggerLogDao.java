package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.TriggerLogEntity;
import org.seasar.dao.annotation.tiger.Arguments;

public interface TriggerLogDao {

	public int insert(TriggerLogEntity entity);

	public int update(TriggerLogEntity entity);

	public int delete(TriggerLogEntity entity);

	@Arguments("ID")
	public TriggerLogEntity selectById(Long id);

	@Arguments("OBJECT_ID")
	public TriggerLogEntity selectByObjectId(Long objectId);

	public List<TriggerLogEntity> selectAll();

}

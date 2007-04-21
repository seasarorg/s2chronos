package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.TriggerEntity;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TriggerEntity.class)
public interface TriggerDao {

	public int insert(TriggerEntity entity);

	public int update(TriggerEntity entity);

	public int delete(TriggerEntity entity);

	@Arguments("ID")
	public TriggerEntity selectById(Long id);

	@Arguments("OBJECT_ID")
	public TriggerEntity selectByObjectId(Long objectId);

	public List<TriggerEntity> selectAll();

}

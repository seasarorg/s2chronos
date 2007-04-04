package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.NonDelayTriggerEntity;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = NonDelayTriggerEntity.class)
public interface NonDelayTriggerDao {

	public int insert(NonDelayTriggerEntity entity);

	public int update(NonDelayTriggerEntity entity);

	public int delete(NonDelayTriggerEntity entity);

	public int insert(List<NonDelayTriggerEntity> entity);

	public int update(List<NonDelayTriggerEntity> entity);

	public int delete(List<NonDelayTriggerEntity> entity);

	public List<NonDelayTriggerEntity> selectAll();

	public NonDelayTriggerEntity selectById(long id);

}

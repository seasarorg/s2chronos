package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.TimedTriggerEntity;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TimedTriggerEntity.class)
public interface TimedTriggerDao {

	public int insert(TimedTriggerEntity entity);

	public int update(TimedTriggerEntity entity);

	public int delete(TimedTriggerEntity entity);

	public int insert(List<TimedTriggerEntity> entity);

	public int update(List<TimedTriggerEntity> entity);

	public int delete(List<TimedTriggerEntity> entity);

	public List<TimedTriggerEntity> selectAll();

	public TimedTriggerEntity selectById(long id);

}

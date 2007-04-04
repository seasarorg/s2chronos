package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.CronTriggerEntity;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = CronTriggerEntity.class)
public interface CronTriggerDao {

	int insert(CronTriggerEntity entity);

	int update(CronTriggerEntity entity);

	int delete(CronTriggerEntity entity);

	int insert(List<CronTriggerEntity> entity);

	int update(List<CronTriggerEntity> entity);

	int delete(List<CronTriggerEntity> entity);

	CronTriggerEntity selectById(long id);

	List<CronTriggerEntity> selectAll();

}

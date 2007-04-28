package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.ScheduleEntity;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = ScheduleEntity.class)
public interface ScheduleDao {

	public List<ScheduleEntity> selectAll();

	@Arguments("OBJECT_ID")
	public ScheduleEntity selectByObjectId(Long id);

}

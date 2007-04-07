package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.ScheduleEntity;

public interface ScheduleDao {

	public List<ScheduleEntity> selectAll();

}

package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.ScheduleEntity;

public interface ScheduleDao {

	public List<ScheduleEntity> selectAll();

}

package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.CronExpressionEntity;

public interface CronExpressionDao {

	public int insert(CronExpressionEntity entity);

	public int update(CronExpressionEntity entity);

	public int delete(CronExpressionEntity entity);

	public CronExpressionEntity selectById(int id);

	public List<CronExpressionEntity> selectAll();

}

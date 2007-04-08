package org.seasar.chronos.store.dao;

import java.util.List;

import org.seasar.chronos.store.entity.ThreadPoolEntity;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = ThreadPoolEntity.class)
public interface ThreadPoolDao {

	public int insert(ThreadPoolEntity entity);

	public int update(ThreadPoolEntity entity);

	public int delete(ThreadPoolEntity entity);

	public ThreadPoolEntity selectById(int id);

	public List<ThreadPoolEntity> selectAll();

}

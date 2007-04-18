package org.seasar.chronos.extension.store.dao;

import java.util.List;

import org.seasar.chronos.extension.store.entity.ThreadPoolEntity;
import org.seasar.dao.annotation.tiger.Arguments;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = ThreadPoolEntity.class)
public interface ThreadPoolDao {

	public int insert(ThreadPoolEntity entity);

	public int update(ThreadPoolEntity entity);

	public int delete(ThreadPoolEntity entity);

	@Arguments("ID")
	public ThreadPoolEntity selectById(Long id);

	public List<ThreadPoolEntity> selectAll();

	@Arguments("OBJECT_ID")
	public ThreadPoolEntity selectByObjectId(Long objectId);

}

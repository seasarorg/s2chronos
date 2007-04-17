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

	@Arguments("THREAD_POOL_CODE")
	public List<ThreadPoolEntity> selectByThreadPoolCode(Integer threadPoolCode);

	public List<ThreadPoolEntity> selectAll();

	@Arguments("THREAD_POOL_CODE")
	public ThreadPoolEntity selectByThreadPoolCodeNewest(Integer threadPoolCode);

}

package org.seasar.chronos.extension.store.impl;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.extension.store.ThreadPoolStore;
import org.seasar.chronos.extension.store.dao.ThreadPoolDao;
import org.seasar.chronos.extension.store.dxo.ThreadPoolDxo;
import org.seasar.chronos.extension.store.entity.ThreadPoolEntity;

public class ThreadPoolStoreImpl implements ThreadPoolStore {

	private ThreadPoolDao threadPoolDao;

	private ThreadPoolDxo threadPoolDxo;

	public TaskThreadPool loadFromStore(Long id) {
		ThreadPoolEntity entity = threadPoolDao.selectById(id);
		if (entity == null) {
			return null;
		}
		return this.threadPoolDxo.toComponent(entity);
	}

	public void loadFromStore(Long id, TaskThreadPool threadPool) {
		ThreadPoolEntity entity = threadPoolDao.selectById(id);
		this.threadPoolDxo.fromEntityToComponent(entity, threadPool);
	}

	public TaskThreadPool loadFromStoreByObjectId(Long objectId) {
		ThreadPoolEntity entity = threadPoolDao.selectByObjectId(objectId);
		if (entity == null) {
			return null;
		}
		return this.threadPoolDxo.toComponent(entity);
	}

	public Long saveToStore(TaskThreadPool threadPool) {
		ThreadPoolEntity threadPoolEntity = this.threadPoolDao
				.selectByObjectId(threadPool.getThreadPoolId());
		if (threadPoolEntity == null) {
			threadPoolEntity = threadPoolDxo.toEntity(threadPool);
			this.threadPoolDao.insert(threadPoolEntity);
		} else {
			threadPoolDxo.fromComponentToEntity(threadPool, threadPoolEntity);
			this.threadPoolDao.update(threadPoolEntity);
		}
		return threadPoolEntity.getId();
	}

	public void setThreadPoolDao(ThreadPoolDao threadPoolDao) {
		this.threadPoolDao = threadPoolDao;
	}

	public void setThreadPoolDxo(ThreadPoolDxo threadPoolDxo) {
		this.threadPoolDxo = threadPoolDxo;
	}

}

package org.seasar.chronos.store;

import org.seasar.chronos.TaskThreadPool;
import org.seasar.chronos.store.dao.ThreadPoolDao;
import org.seasar.chronos.store.dxo.ThreadPoolDxo;
import org.seasar.chronos.store.entity.ThreadPoolEntity;
import org.seasar.framework.exception.SQLRuntimeException;

public class ThreadPoolStore {

	private ThreadPoolDao threadPoolDao;

	private ThreadPoolDxo threadPoolDxo;

	public TaskThreadPool loadFromStore(int id) {
		ThreadPoolEntity entity = threadPoolDao.selectById(id);
		if (entity == null) {
			return null;
		}
		return this.threadPoolDxo.toComponent(entity);
	}

	public void loadFromStore(int id, TaskThreadPool threadPool) {
		ThreadPoolEntity entity = threadPoolDao.selectById(id);
		this.threadPoolDxo.fromEntityFromComponent(entity, threadPool);
	}

	public void saveToStore(TaskThreadPool threadPool) {
		ThreadPoolEntity entity = threadPoolDxo.toEntity(threadPool);
		try {
			this.threadPoolDao.update(entity);
		} catch (SQLRuntimeException ex) {
			this.threadPoolDao.insert(entity);
		}
	}

	public void setThreadPoolDao(ThreadPoolDao threadPoolDao) {
		this.threadPoolDao = threadPoolDao;
	}

	public void setThreadPoolDxo(ThreadPoolDxo threadPoolDxo) {
		this.threadPoolDxo = threadPoolDxo;
	}

}

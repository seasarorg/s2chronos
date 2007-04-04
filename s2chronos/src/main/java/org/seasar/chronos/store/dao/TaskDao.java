package org.seasar.chronos.store.dao;

import org.seasar.chronos.store.entity.TaskEntity;
import org.seasar.dao.annotation.tiger.S2Dao;

@S2Dao(bean = TaskEntity.class)
public interface TaskDao {

}

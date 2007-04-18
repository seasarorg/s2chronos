package org.seasar.chronos.extension.store;

import java.util.ArrayList;
import java.util.List;

import org.seasar.chronos.extension.store.dao.ScheduleDao;
import org.seasar.chronos.extension.store.entity.ScheduleEntity;

public class ScheduleStore {

	private ScheduleDao scheduleDao;

	public List<RecoveryTaskInfo> getRecoveryTaskNames() {
		List<ScheduleEntity> entities = scheduleDao.selectAll();
		List<RecoveryTaskInfo> result = new ArrayList<RecoveryTaskInfo>();
		for (ScheduleEntity entity : entities) {
			RecoveryTaskInfo rti = new RecoveryTaskInfo();
			rti.setTaskId(entity.getTaskId());
			rti.setTaskName(entity.getTaskName());
			result.add(rti);
		}
		return result;
	}
}
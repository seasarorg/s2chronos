package org.seasar.chronos.core.schedule;

import org.seasar.chronos.core.TaskScheduleEntry;
import org.seasar.chronos.core.util.ObjectUtil;

public abstract class AbstractScheduleEntry implements TaskScheduleEntry {

	private Long scheduleId;

	public Long getScheduleId() {
		if (this.scheduleId == null) {
			this.scheduleId = ObjectUtil.generateObjectId();
		}
		return this.scheduleId;
	}

	public void load() {

	}

	public void save() {

	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

}

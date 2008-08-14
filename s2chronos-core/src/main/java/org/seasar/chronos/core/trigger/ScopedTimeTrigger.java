package org.seasar.chronos.core.trigger;

import java.util.Date;

import org.seasar.chronos.core.TaskTrigger;

@SuppressWarnings("serial")
public class ScopedTimeTrigger extends TriggerWrapper {

	private Date scopedStartTime;
	private Date scopedEndTime;

	@Override
	public boolean isEndTask() {
		boolean endScopedTimeCheck = false;
		if (scopedEndTime != null) {
			endScopedTimeCheck = (System.currentTimeMillis() <= scopedEndTime
					.getTime());
		}
		return endScopedTimeCheck && super.isEndTask();
	}

	@Override
	public boolean isStartTask() {
		boolean startScopedTimeCheck = false;
		if (scopedStartTime != null) {
			startScopedTimeCheck = (System.currentTimeMillis() >= scopedStartTime
					.getTime());
		}
		return startScopedTimeCheck && super.isStartTask();
	}

	public ScopedTimeTrigger(TaskTrigger taskTrigger) {
		super(taskTrigger);
	}

	public Date getScopedStartTime() {
		return (Date) scopedStartTime.clone();
	}

	public void setScopedStartTime(Date scopedStartTime) {
		this.scopedStartTime = (Date) scopedStartTime.clone();
	}

	public Date getScopedEndTime() {
		return (Date) scopedEndTime.clone();
	}

	public void setScopedEndTime(Date scopedEndTime) {
		this.scopedEndTime = (Date) scopedEndTime.clone();
	}

}

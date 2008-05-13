package org.seasar.chronos.core.trigger;

import java.util.Date;

public class CTimedTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private Date startTime;

	private Date endTime;

	public CTimedTrigger() {
		super("timedTrigger");
	}

	@Override
	public boolean isReSchedule() {
		return false;
	}

	public void setStartTime(Date startDate) {
		this.startTime = (Date) startDate.clone();
	}

	public Date getStartTime() {
		return (Date) startTime.clone();
	}

	public void setEndTime(Date endDate) {
		this.endTime = (Date) endDate.clone();
	}

	public Date getEndTime() {
		return (Date) endTime.clone();
	}

	public boolean isStartTask() {

		if (this.isExecute()) {
			return false;
		}

		boolean startTimeCheck = false;

		// 開始時刻の確認
		if (startTime != null) {
			startTimeCheck = (System.currentTimeMillis() >= startTime.getTime());
		}

		return startTimeCheck;
	}

	public boolean isEndTask() {

		boolean endTimeCheck = false;

		// 終了時刻の確認
		if (endTime != null) {
			endTimeCheck = (System.currentTimeMillis() >= endTime.getTime());
		}

		return endTimeCheck;
	}

	public void setStartTask(boolean startTask) {

	}

	public void setEndTask(boolean endTask) {

	}

}

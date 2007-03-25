package org.seasar.chronos.trigger;

import java.util.Date;

public class Trigger extends AbstractTrigger {

	private Date startTime;

	private Date endTime;

	public Trigger() {

	}

	public Trigger(String name) {
		super(name);
	}

	public void setStartTime(Date startDate) {
		this.startTime = startDate;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setEndTime(Date endDate) {
		this.endTime = endDate;
	}

	public Date getEndTime() {
		return endTime;
	}

	public boolean getStartTask() {

		if (this.isExecuted()) {
			return false;
		}

		boolean startTimeCheck = false;

		// 開始時刻の確認
		if (startTime != null) {
			startTimeCheck = (System.currentTimeMillis() >= startTime.getTime());
		}

		return startTimeCheck;
	}

	public boolean getEndTask() {

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

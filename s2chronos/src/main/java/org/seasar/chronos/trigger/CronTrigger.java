package org.seasar.chronos.trigger;

import java.util.ArrayList;
import java.util.Date;

import org.seasar.chronos.trigger.cron.CronExpression;

public class CronTrigger extends AbstractTrigger {

	private CronExpression expression;

	private ArrayList<Date> startTimeList;

	public CronTrigger() {

	}

	public CronTrigger(String name) {
		super(name);
	}

	public void setCronExpression(String cronExpression) {
		this.expression = new CronExpression(cronExpression);
		this.expression.buildNextTime();
		this.startTimeList = this.expression.getStartTimes();
	}

	@Override
	public void setExecuted(boolean executed) {
		// this.expression.buildNextTime();
	}

	public boolean getStartTask() {

		long nowTime = System.currentTimeMillis();
		boolean startTimeCheck = false;

		// 開始時刻の確認
		if (startTimeList != null) {
			int size = startTimeList.size();
			for (int i = 0; i < size; i++) {
				Date startTime = startTimeList.get(i);
				startTimeCheck = (nowTime >= startTime.getTime());
				if (startTimeCheck) {
					startTimeList.remove(i);
					break;
				}
			}
			if (startTimeList.size() == 0) {
				this.expression.buildNextTime();
				this.startTimeList = this.expression.getStartTimes();
			}
		}

		return startTimeCheck;
	}

	public boolean getEndTask() {
		return false;
	}

	public void setEndTask(boolean endTask) {

	}

	public void setStartTask(boolean startTask) {

	}

}

package org.seasar.chronos.trigger.impl;

import java.util.ArrayList;
import java.util.Date;


public class GenericCronTrigger extends AbstractTrigger {

	private CronExpression expression;

	private ArrayList<Date> startTimeList;

	public GenericCronTrigger() {

	}

	public GenericCronTrigger(String name) {
		super(name);
	}

	public void setCronExpression(String cronExpression) {
		this.expression = new CronExpression(cronExpression);
		this.expression.buildNextTime();
		this.startTimeList = this.expression.getStartTimes();
	}

	public boolean canEnd() {
		return false;
	}

	public boolean canStart() {

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

	@Override
	public void setExecuted(boolean executed) {
		// this.expression.buildNextTime();
	}

}

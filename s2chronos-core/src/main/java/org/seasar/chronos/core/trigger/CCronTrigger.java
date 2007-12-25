package org.seasar.chronos.core.trigger;

import java.util.ArrayList;
import java.util.Date;

import org.seasar.chronos.core.trigger.cron.CronExpression;
import org.seasar.framework.log.Logger;

public class CCronTrigger extends AbstractTrigger {

	private static Logger log = Logger.getLogger(CCronTrigger.class);

	private static final long serialVersionUID = 1L;

	private CronExpression expression;

	private ArrayList<Date> startTimeList;

	public CCronTrigger() {
		super("cronTrigger");
	}

	public CCronTrigger(String cronExpression) {
		super("cronTrigger");
		this.setExpression(cronExpression);
	}

	@Override
	public boolean isReSchedule() {
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = super.equals(obj);
		CCronTrigger src = (CCronTrigger) obj;
		if (this.expression != null) {
			result = result & expression.equals(src.expression);
		}
		if (this.startTimeList != null) {
			result = result & startTimeList.equals(src.startTimeList);
		}
		return result;
	}

	public String getCronExpression() {
		return this.expression.getCronExprssion();
	}

	public boolean isEndTask() {
		return false;
	}

	public boolean isStartTask() {
		long nowTime = System.currentTimeMillis();
		boolean startTimeCheck = false;
		// 開始時刻の確認
		if (startTimeList != null) {
			int size = startTimeList.size();
			for (int i = 0; i < size; i++) {
				Date startTime = startTimeList.get(i);
				startTimeCheck = (nowTime >= startTime.getTime());
				if (startTimeCheck) {
					log.debug("startTime = " + startTime);
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

	public void setExpression(String cronExpression) {
		this.expression = new CronExpression(cronExpression);
		this.expression.buildNextTime();
		this.startTimeList = this.expression.getStartTimes();
	}

	public String getExpression() {
		return this.expression.getCronExprssion();
	}

	public void setEndTask(Boolean endTask) {

	}

	@Override
	public void setExecute(Boolean executed) {
		// this.expression.buildNextTime();
	}

	public void setStartTask(Boolean startTask) {

	}

}

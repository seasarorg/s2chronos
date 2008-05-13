package org.seasar.chronos.core.trigger;

import java.util.Calendar;

public class CMonthlyTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public boolean isEndTask() {
		return false;
	}

	public synchronized boolean isStartTask() {
		Calendar calendar = Calendar.getInstance();
		int max = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, max);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
			return true;
		}
		return false;
	}

	public void setEndTask(boolean endTask) {

	}

	public void setStartTask(boolean startTask) {

	}

	@Override
	public boolean isReSchedule() {
		return true;
	}

}

package org.seasar.chronos.core.trigger;

import java.util.Calendar;

public class CMonthlyTrigger extends AbstractTrigger {

	private static final long serialVersionUID = 1L;

	private int currentMonth;

	public boolean isEndTask() {
		return false;
	}

	public synchronized boolean isStartTask() {
		Calendar calendar = Calendar.getInstance();
		int max = calendar.getActualMaximum(Calendar.DATE);
		calendar.set(Calendar.DATE, max);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);

		currentMonth = calendar.get(Calendar.MONTH);

		if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				;
			}
			return true;
		}
		return false;
	}

	public void setEndTask(Boolean endTask) {

	}

	public void setStartTask(Boolean startTask) {

	}

	@Override
	public boolean isReSchedule() {
		return true;
	}

}

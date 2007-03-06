package org.seasar.s2chronos.trigger.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import org.seasar.framework.log.Logger;

public class CronExpression {

	private static final String TOKEN_DELIMITER = " \t";

	private static final String SLISH = "/";

	private static final String ASTERISK_SLISH = "*/";

	private static final String DELIMITER = ",";

	private static final String ASTERISK = "*";

	private Logger log = Logger.getLogger(CronExpression.class);

	private String cronExpression;

	private ArrayList<Date> startTimeList;

	public CronExpression() {

	}

	public CronExpression(String cronExpression) {
		this.setCronExpression(cronExpression);
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public void buildNextTime() {
		this.startTimeList = this.buildExpression(this.cronExpression);
	}

	public ArrayList<Date> getStartTimes() {
		return this.startTimeList;
	}

	private void buildInterval(ArrayList<String> list, int now, int min, int max) {
		String option = list.get(0);
		if (option.contains(ASTERISK_SLISH)) {
			String[] ov = option.split(SLISH);
			int ovi = Integer.parseInt(ov[1]);
			list.clear();
			for (int mi = now; mi <= max; mi += ovi) {
				list.add(Integer.toString(mi));
			}
			if (now > min) {
				for (int mi = min; mi < now; mi++) {
					list.add(Integer.toString(mi));
				}
			}
		}
	}

	private ArrayList<Date> buildExpression(String cronExpression) {
		int month;
		int day;
		int hour;
		int minute;
		Vector<ArrayList<String>> digit = new Vector<ArrayList<String>>();
		int index = 0;
		StringTokenizer exprsTok = new StringTokenizer(cronExpression, TOKEN_DELIMITER,
				false);
		while (exprsTok.hasMoreTokens()) {
			String token = exprsTok.nextToken().trim();
			StringTokenizer vTok = new StringTokenizer(token, DELIMITER);
			ArrayList<String> list = new ArrayList<String>();
			while (vTok.hasMoreTokens()) {
				String v = vTok.nextToken();
				list.add(v);
				log.debug("index = " + index + ", v=" + v);
			}
			digit.add(list);
			index++;
		}
		ArrayList<String> monthList = digit.get(3);
		ArrayList<String> dayList = digit.get(2);
		ArrayList<String> hourList = digit.get(1);
		ArrayList<String> minList = digit.get(0);
		Calendar calendar = Calendar.getInstance();
		buildInterval(monthList, calendar.get(Calendar.MONTH), calendar
				.getMinimum(Calendar.MONTH), calendar
				.getMaximum(Calendar.MONTH));
		buildInterval(dayList, calendar.get(Calendar.DATE), calendar
				.getMinimum(Calendar.DATE), calendar.getMaximum(Calendar.DATE));
		buildInterval(hourList, calendar.get(Calendar.HOUR_OF_DAY), calendar
				.getMinimum(Calendar.HOUR_OF_DAY), calendar
				.getMaximum(Calendar.HOUR_OF_DAY));
		buildInterval(minList, calendar.get(Calendar.MINUTE) + 1, calendar
				.getMinimum(Calendar.MINUTE), calendar
				.getMaximum(Calendar.MINUTE));
		ArrayList<Date> dateList = new ArrayList<Date>();
		int n = monthList.size();
		for (int i = 0; i < n; i++) {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.SECOND, 0);
			String monthDigit = monthList.get(i);
			if (!ASTERISK.equals(monthDigit)) {
				month = Integer.parseInt(monthDigit);
				// 古い場合は
				if (calendar.get(Calendar.MONTH) > month) {
					// 一年進みます
					calendar.add(Calendar.YEAR, 1);
				}
				calendar.set(Calendar.MONTH, month);
			} else {
				month = calendar.get(Calendar.MONTH);
			}
			int m = dayList.size();
			for (int j = 0; j < m; j++) {
				String dayDigit = dayList.get(j);
				if (!ASTERISK.equals(dayDigit)) {
					day = Integer.parseInt(dayDigit);
					// 古い場合は
					if (calendar.get(Calendar.DATE) > day) {
						// 1ヶ月進みます
						calendar.add(Calendar.MONTH, 1);
					}
					calendar.set(Calendar.DATE, day);
				} else {
					day = calendar.get(Calendar.DATE);
				}
				int o = hourList.size();
				for (int k = 0; k < o; k++) {
					String hourDigit = hourList.get(k);
					if (!ASTERISK.equals(hourDigit)) {
						hour = Integer.parseInt(hourDigit);
						// 古い場合は
						if (calendar.get(Calendar.HOUR_OF_DAY) > hour) {
							// 一日進みます
							calendar.add(Calendar.DATE, 1);
						}
						calendar.set(Calendar.HOUR_OF_DAY, hour);
					} else {
						hour = calendar.get(Calendar.HOUR_OF_DAY);
					}
					int p = minList.size();
					for (int h = 0; h < p; h++) {
						String minDigit = minList.get(h);
						if (!ASTERISK.equals(minDigit)) {
							minute = Integer.parseInt(minDigit);
							// 古い場合は
							if (calendar.get(Calendar.MINUTE) > minute) {
								// 1時間進みます
								calendar.add(Calendar.HOUR_OF_DAY, 1);
							}
							calendar.set(Calendar.MINUTE, minute);
						} else {
							minute = calendar.get(Calendar.MINUTE);
						}
						Date date = calendar.getTime();
						log.debug("nextTime = " + date);
						dateList.add(date);
					}
				}
			}
		}

		return dateList;
	}

}

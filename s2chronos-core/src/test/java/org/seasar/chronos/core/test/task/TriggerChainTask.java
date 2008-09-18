package org.seasar.chronos.core.test.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.trigger.CTimedTrigger;
import org.seasar.chronos.core.trigger.TriggerChain;

@Task
public class TriggerChainTask {

	private TriggerChain trigger = new TriggerChain();

	public TaskTrigger getTrigger() {
		return trigger;
	}

	public void initialize() {
		Date date1 = null;
		Date date2 = null;

		CTimedTrigger a = new CTimedTrigger();
		CTimedTrigger b = new CTimedTrigger();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			date1 = sdf.parse("20080918100000");// 2008/09/18 10:00:00に起動
			date2 = sdf.parse("20080918100030");// 2008/09/18 10:00:30に起動
		} catch (ParseException e) {
			e.printStackTrace();
		}

		a.setStartTime(date1);
		b.setStartTime(date2);

		trigger.addTrigger(a);
		trigger.addTrigger(b);

		trigger.setReScheduleTask(true);
	}

	public void doExecute() {
		System.out.println("doExecute()が実行されました");
	}

	public void end() {
		trigger.setReScheduleTask(!trigger.isExecuted());
	}

	public void destroy() {

	}
}

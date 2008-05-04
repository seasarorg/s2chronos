package org.seasar.chronos.teeda.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;

@Task
@CronTrigger(expression = "*/3 * * * *")
public class S2SessionTimerTask {

	public void initialize() {

	}

	public void doExpire() {

	}

	public void destory() {

	}

}

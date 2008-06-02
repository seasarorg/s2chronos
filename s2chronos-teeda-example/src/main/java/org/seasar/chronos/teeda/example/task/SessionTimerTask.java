package org.seasar.chronos.teeda.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;

@Task
@CronTrigger(expression = "0 */3 * * * *")
public class SessionTimerTask {

	@NextTask("expire")
	public void initialize() {

	}

	public void doExpire() {

	}

	public void destory() {

	}

}

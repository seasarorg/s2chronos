package org.seasar.chronos.teeda.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.framework.log.Logger;

@Task
@CronTrigger(expression = "0 */3 * * * ?")
public class SessionTimerTask {

	private static Logger log = Logger.getLogger(SessionTimerTask.class);

	@NextTask("expire")
	public void initialize() {
		log.info("SessionTimerTask#initialize");
	}

	public void doExpire() {
		log.info("SessionTimerTask#doExpire");
	}

	public void destory() {
		log.info("SessionTimerTask#destory");
	}

}

package org.seasar.chronos.sastruts.example.task;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.task.method.NextTask;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.log.Logger;

@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class SessionTimerTask {

	public JdbcManager jdbcManager;

	private static Logger log = Logger.getLogger(SessionTimerTask.class);

	@NextTask("expire")
	public void initialize() {
		log.info("SessionTimerTask#initialize");
	}

	public void doExpire() {
		log.info("SessionTimerTask#doExpire");

		/*
		 * s2SessionDao.deleteOldSession(new Timestamp(
		 * System.currentTimeMillis() - 36000));
		 */
	}
}

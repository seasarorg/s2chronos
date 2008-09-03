package org.seasar.chronos.sastruts.example.task;

import java.sql.Timestamp;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.extension.jdbc.JdbcManager;

@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class SessionTimerTask {

	public JdbcManager jdbcManager;

	public void doExecute() {
		jdbcManager.updateBySql("DELETE FROM S2SESSION WHERE LAST_ACCESS < ?",
				Timestamp.class).params(
				new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000))
				.execute();
	}

}

package org.seasar.chronos.sastruts.example.task;

import java.sql.Timestamp;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.log.Logger;

/**
 * 古くなったS2SESSION情報を削除します。
 */
@Task(autoSchedule=false)
@CronTrigger(expression = "0 */1 * * * ?")
public class SessionTimerTask {
	
	public String testName;

	/** Logger */
	private final Logger log = Logger.getLogger(SessionTimerTask.class);

	/** JdbcManager */
	public JdbcManager jdbcManager;

	public SessionTimerTask(){
		testName = "abc";
	}
	
	/**
	 * 5分前のセッション情報を削除します。
	 */
	public void doExecute() {
		log.debug(">>>>>> testName="+testName+","+this);
		jdbcManager.updateBySql("DELETE FROM S2SESSION WHERE LAST_ACCESS < ?",
				Timestamp.class).params(
				new Timestamp(System.currentTimeMillis() - 5 * 60 * 1000))
				.execute();
	}

	/**
	 * 例外をキャッチします。
	 * 
	 * @param ex
	 *            例外
	 */
	public void catchException(Exception ex) {
		log.error(ex);
	}

}

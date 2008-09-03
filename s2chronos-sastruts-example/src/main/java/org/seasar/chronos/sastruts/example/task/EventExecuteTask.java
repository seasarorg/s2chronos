package org.seasar.chronos.sastruts.example.task;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import org.seasar.chronos.core.annotation.task.Task;
import org.seasar.chronos.core.annotation.trigger.CronTrigger;
import org.seasar.chronos.sastruts.example.entity.Event;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.log.Logger;

@Task
@CronTrigger(expression = "0 */1 * * * ?")
public class EventExecuteTask {

	private final Logger log = Logger.getLogger(EventExecuteTask.class);

	public JdbcManager jdbcManager;

	public void doExecute() {
		List<Event> eventList = jdbcManager.from(Event.class).where(
				"EVENT_DATE < ? AND EVENT_STATUS = ?",
				new Timestamp(System.currentTimeMillis()), Event.STATUS_NONE)
				.orderBy("eventId").getResultList();
		for (Event e : eventList) {
			e.eventStatus = Event.STATUS_ING;
			this.jdbcManager.update(e).execute();
			e.eventStatus = Event.STATUS_DONE;
			log.info("start:" + e.eventText);
			try {
				Process p = Runtime.getRuntime().exec(e.eventText);
				p.waitFor();
			} catch (InterruptedException e1) {
				;
			} catch (IOException e2) {
				e.eventStatus = Event.STATUS_ERR;
			}
			log.info("done:" + e.eventText);
			this.jdbcManager.update(e).execute();
		}
	}

	public void catchException(Exception ex) {
		log.error(ex);
	}

}

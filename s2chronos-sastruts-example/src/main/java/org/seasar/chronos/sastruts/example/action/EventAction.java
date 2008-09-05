package org.seasar.chronos.sastruts.example.action;

import java.util.List;

import javax.annotation.Resource;

import org.seasar.chronos.sastruts.example.entity.Event;
import org.seasar.chronos.sastruts.example.form.EventForm;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.util.tiger.CollectionsUtil;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class EventAction {

	@Resource
	protected JdbcManager jdbcManager;

	@ActionForm
	@Resource
	protected EventForm eventForm;

	public List<BeanMap> resultEventItems = CollectionsUtil.newArrayList();

	@Execute(validator = false)
	public String index() {
		eventForm.initialize();
		List<Event> eventList = jdbcManager.from(Event.class)
				.orderBy("eventId").getResultList();
		for (Event entity : eventList) {
			resultEventItems.add(Beans.createAndCopy(BeanMap.class, entity)
					.dateConverter(EventForm.DATE_PATTERN, "eventDate")
					.execute());
		}
		return "index.html";
	}

	@Execute(input = "index.html")
	public String submit() {
		Event event = Beans.createAndCopy(Event.class, eventForm)
				.dateConverter(EventForm.DATE_PATTERN, "eventDate").execute();
		event.eventStatus = Event.STATUS_NONE;
		this.jdbcManager.insert(event).execute();
		return "../event/?redirect=true";
	}

	@Execute(validator = false)
	public String delete() {
		this.jdbcManager.updateBySql("DELETE FROM EVENT WHERE EVENT_ID = ? ",
				Long.class).params(this.eventForm.deleteEventId).execute();
		return "../event/?redirect=true";
	}

}

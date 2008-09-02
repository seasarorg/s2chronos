package org.seasar.chronos.sastruts.example.action;

import java.util.List;

import javax.annotation.Resource;

import org.seasar.chronos.sastruts.example.entity.Event;
import org.seasar.chronos.sastruts.example.form.EventForm;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.framework.beans.util.BeanMap;
import org.seasar.framework.beans.util.Beans;
import org.seasar.struts.annotation.ActionForm;
import org.seasar.struts.annotation.Execute;

public class EventAction {

	@Resource
	protected JdbcManager jdbcManager;

	@ActionForm
	@Resource
	protected EventForm eventForm;

	public List<BeanMap> eventItems;

	@Execute(validator = false)
	public String index() {
		List<Event> eventList = jdbcManager.from(Event.class)
				.orderBy("eventId").getResultList();
		Beans.createAndCopy(BeanMap.class, eventList).execute();
		return "index.html";
	}

	@Execute(input = "index.html")
	public String submit() {
		return null;
	}

}

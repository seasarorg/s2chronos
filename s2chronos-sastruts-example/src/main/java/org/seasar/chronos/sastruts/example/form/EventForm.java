package org.seasar.chronos.sastruts.example.form;

import java.util.Date;

import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class EventForm {

	@Required
	@DateType
	public Date eventDate;

	@Required
	@Maxlength(maxlength = 255)
	public String eventText;

}

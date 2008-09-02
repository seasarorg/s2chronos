package org.seasar.chronos.sastruts.example.form;

import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

public class EventForm {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";

	@Required
	@DateType(datePattern = DATE_PATTERN)
	public String eventDate;

	@Required
	@Maxlength(maxlength = 255)
	public String eventText;

}

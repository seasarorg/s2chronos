package org.seasar.chronos.sastruts.example.form;

import java.io.Serializable;

import org.seasar.framework.container.annotation.tiger.Component;
import org.seasar.framework.container.annotation.tiger.InstanceType;
import org.seasar.struts.annotation.DateType;
import org.seasar.struts.annotation.Maxlength;
import org.seasar.struts.annotation.Required;

@Component(instance = InstanceType.SESSION)
@SuppressWarnings("serial")
public class EventForm implements Serializable {

	public static final String DATE_PATTERN = "yyyy/MM/dd HH:mm:ss";

	@Required
	@DateType(datePattern = DATE_PATTERN)
	public String eventDate;

	@Required
	@Maxlength(maxlength = 255)
	public String eventText;

}

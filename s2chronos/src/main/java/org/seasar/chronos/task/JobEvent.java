package org.seasar.chronos.task;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface JobEvent {

	public void setSender(Object jobComponent);

	public Object getSender();

	public void setMessage(String message);

	public String getMessage();

	public boolean dispatchChildEvent(List jobComponentItems)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException;

	public boolean dispatchParentEvent(Object parent)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException, SecurityException, NoSuchMethodException;

}

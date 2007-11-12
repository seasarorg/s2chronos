package org.seasar.chronos.core;

import java.io.Serializable;

public interface TaskTrigger extends Serializable {

	public String getDescription();

	public boolean isEndTask();

	public Long getTriggerId();

	public String getName();

	public boolean isStartTask();

	public Object getTask();

	public boolean isExecute();

	public void setDescription(String description);

	public void setEndTask(Boolean endTask);

	public void setExecute(Boolean execute);

	public void setTriggerId(Long id);

	public void setName(String name);

	public void setStartTask(Boolean startTask);

	public void setTask(Object task);

	public boolean isReSchedule();

}
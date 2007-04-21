package org.seasar.chronos.core;

public interface TaskTrigger extends Serializable {

	public String getDescription();

	public Boolean getEndTask();

	public Long getTriggerId();

	public String getName();

	public Boolean getStartTask();

	public Object getTask();

	public Boolean isExecute();

	public void setDescription(String description);

	public void setEndTask(Boolean endTask);

	public void setExecute(Boolean execute);

	public void setTriggerId(Long id);

	public void setName(String name);

	public void setStartTask(Boolean startTask);

	public void setTask(Object task);

}
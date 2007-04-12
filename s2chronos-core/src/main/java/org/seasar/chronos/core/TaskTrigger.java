package org.seasar.chronos.core;

public interface TaskTrigger extends Serializable {

	public Boolean getStartTask();

	public void setStartTask(Boolean startTask);

	public Boolean getEndTask();

	public void setEndTask(Boolean endTask);

	public String getDescription();

	public Integer getId();

	public void setId(Integer id);

	public Object getTask();

	public String getName();

	public Boolean isExecute();

	public void setDescription(String description);

	public void setExecute(Boolean execute);

	public void setTask(Object task);

	public void setName(String name);

}
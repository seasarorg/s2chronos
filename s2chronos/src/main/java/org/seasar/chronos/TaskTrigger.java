package org.seasar.chronos;

public interface TaskTrigger extends Serializable {

	public boolean getStartTask();

	public void setStartTask(boolean startTask);

	public boolean getEndTask();

	public void setEndTask(boolean endTask);

	public String getDescription();

	public int getId();

	public void setId(int id);

	public Object getTask();

	public String getName();

	public boolean isExecute();

	public void setDescription(String description);

	public void setExecute(boolean executed);

	public void setTask(Object task);

	public void setName(String name);

}
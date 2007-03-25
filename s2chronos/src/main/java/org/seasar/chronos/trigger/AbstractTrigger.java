package org.seasar.chronos.trigger;

import org.seasar.chronos.TaskTrigger;

public abstract class AbstractTrigger implements TaskTrigger {

	private long id;

	private String name;

	private Object task;

	private String description;

	private boolean executed;

	public AbstractTrigger() {

	}

	public AbstractTrigger(String name) {
		this.setName(name);
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return this.id;
	}

	public Object getTask() {
		return task;
	}

	public String getName() {
		return name;
	}

	public boolean isExecuted() {
		return this.executed;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setName(String name) {
		this.name = name;
	}

}

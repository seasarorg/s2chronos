package org.seasar.chronos.trigger;

import org.seasar.chronos.Serializable;
import org.seasar.chronos.TaskTrigger;
import org.seasar.chronos.store.trigger.TriggerStore;

public abstract class AbstractTrigger implements TaskTrigger, Serializable {

	private TriggerStore store;

	public void load() {
		store.loadFromStore(this.getId(), this);
	}

	public void save() {
		store.saveToStore(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractTrigger) {
			boolean result = true;
			AbstractTrigger src = (AbstractTrigger) obj;
			result = result & this.id == src.id;
			if (this.name != null) {
				result = result & this.name.equals(src.name);
			}
			if (this.task != null) {
				result = result & this.task.equals(src.task);
			}
			if (this.description != null) {
				result = result & this.description.equals(src.description);
			}
			result = result & this.execute == src.execute;
			return result;
		} else {
			return super.equals(obj);
		}
	}

	private int id;

	private String name;

	private Object task;

	private String description;

	private boolean execute;

	public AbstractTrigger() {

	}

	public AbstractTrigger(String name) {
		this.setName(name);
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return this.id;
	}

	public Object getTask() {
		return task;
	}

	public String getName() {
		return name;
	}

	public boolean isExecute() {
		return this.execute;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExecute(boolean executed) {
		this.execute = executed;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setName(String name) {
		this.name = name;
	}

}

package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.util.ObjectUtil;

public abstract class AbstractTrigger implements TaskTrigger {

	private Long id;

	private String name;

	private Object task;

	private String description;

	private Boolean execute;

	public AbstractTrigger() {

	}

	public AbstractTrigger(String name) {
		this.setName(name);
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

	public String getDescription() {
		return description;
	}

	public Long getId() {
		if (this.id == null) {
			this.id = ObjectUtil.generateObjectId();
		}
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Object getTask() {
		return task;
	}

	public Boolean isExecute() {
		return this.execute;
	}

	public void load() {

	}

	public void save() {

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExecute(Boolean executed) {
		this.execute = executed;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTask(Object task) {
		this.task = task;
	}

}

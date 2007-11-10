package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.util.ObjectUtil;

public abstract class AbstractTrigger implements TaskTrigger {

	private Long triggerId;

	private String name;

	private Object task;

	private String description;

	private boolean execute;

	private boolean reSchedule;

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
			if (this.triggerId != null) {
				result = result & this.triggerId.equals(src.triggerId);
			}
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

	public String getName() {
		return name;
	}

	public Object getTask() {
		return task;
	}

	public Long getTriggerId() {
		if (this.triggerId == null) {
			this.triggerId = ObjectUtil.generateObjectId();
		}
		return this.triggerId;
	}

	public boolean isExecute() {
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

	public void setName(String name) {
		this.name = name;
	}

	public void setTask(Object task) {
		this.task = task;
	}

	public void setTriggerId(Long id) {
		this.triggerId = id;
	}

	public boolean isReSchedule() {
		return reSchedule;
	}

	public void setReSchedule(Boolean reSchedule) {
		this.reSchedule = reSchedule;
	}

}

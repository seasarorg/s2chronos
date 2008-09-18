/* 
 * Copyright 2008 the Seasar Foundation and the Others.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not 
 * use this file except in compliance with the License. You may obtain a copy 
 * of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the 
 * License for the specific language governing permissions and limitations 
 * under the License.
 * 
 */

package org.seasar.chronos.core.trigger;

import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.util.ObjectUtil;

public abstract class AbstractTrigger implements TaskTrigger {

	private Long triggerId;

	private String name;

	private Object task;

	private String description;

	private boolean executed;

	private boolean executing;

	private boolean reScheduleTask;

	private boolean shutdownTask;

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
			result = result & this.executing == src.executing;
			return result;
		} else {
			return super.equals(obj);
		}
	}

	@Override
	public int hashCode() {
		return super.hashCode();
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

	public boolean isExecuting() {
		return this.executing;
	}

	public void load() {

	}

	public void save() {

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExecuting(boolean executing) {
		this.executing = executing;
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

	public boolean isReScheduleTask() {
		return reScheduleTask;
	}

	public void setReScheduleTask(boolean reScheduleTask) {
		this.reScheduleTask = reScheduleTask;
	}

	public boolean isShutdownTask() {
		return shutdownTask;
	}

	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

}

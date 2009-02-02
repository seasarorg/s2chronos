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

package org.seasar.chronos.core.model.trigger;

import org.seasar.chronos.core.model.TaskTrigger;

public class TriggerWrapper implements TaskTrigger {

	private static final long serialVersionUID = 1L;

	private TaskTrigger taskTrigger;

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public TriggerWrapper(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	public boolean isEndTask() {
		return taskTrigger.isEndTask();
	}

	public boolean isStartTask() {
		return taskTrigger.isStartTask();
	}

	public void setEndTask(boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	public void setStartTask(boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

	public String getDescription() {
		return taskTrigger.getDescription();
	}

	public String getName() {
		return taskTrigger.getName();
	}

	public Object getTask() {
		return taskTrigger.getTask();
	}

	public Long getTriggerId() {
		return taskTrigger.getTriggerId();
	}

	public boolean isExecuted() {
		return taskTrigger.isExecuted();
	}

	public boolean isExecuting() {
		return taskTrigger.isExecuting();
	}

	public boolean isReScheduleTask() {
		return taskTrigger.isReScheduleTask();
	}

	public boolean isShutdownTask() {
		return taskTrigger.isShutdownTask();
	}

	public void setDescription(String description) {
		taskTrigger.setDescription(description);
	}

	public void setExecuted(boolean executed) {
		taskTrigger.setExecuted(executed);
	}

	public void setExecuting(boolean executing) {
		taskTrigger.setExecuting(executing);
	}

	public void setName(String name) {
		taskTrigger.setName(name);
	}

	public void setReScheduleTask(boolean reScheduleTask) {
		taskTrigger.setReScheduleTask(reScheduleTask);
	}

	public void setShutdownTask(boolean shutdownTask) {
		taskTrigger.setShutdownTask(shutdownTask);
	}

	public void setTask(Object task) {
		taskTrigger.setTask(task);
	}

	public void setTriggerId(Long id) {
		taskTrigger.setTriggerId(id);
	}

	public boolean isForceUnScheduleTask() {
		return taskTrigger.isForceUnScheduleTask();
	}

	public void setForceUnScheduleTask(boolean forceUnScheduleTask) {
		taskTrigger.setForceUnScheduleTask(forceUnScheduleTask);
	}

}

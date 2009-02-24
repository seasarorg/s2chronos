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

/**
 * {@link TaskTrigger}のラッパークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class TriggerWrapper implements TaskTrigger {
	private final TaskTrigger taskTrigger;

	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * コンストラクタです．
	 * 
	 * @param taskTrigger
	 *            ラップする対象の{@link TaskTrigger}
	 */
	public TriggerWrapper(TaskTrigger taskTrigger) {
		this.taskTrigger = taskTrigger;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isEndTask()
	 */
	public boolean isEndTask() {
		return taskTrigger.isEndTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
	public boolean isStartTask() {
		return taskTrigger.isStartTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setEndTask(boolean)
	 */
	public void setEndTask(boolean endTask) {
		taskTrigger.setEndTask(endTask);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setStartTask(boolean)
	 */
	public void setStartTask(boolean startTask) {
		taskTrigger.setStartTask(startTask);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getDescription()
	 */
	public String getDescription() {
		return taskTrigger.getDescription();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getName()
	 */
	public String getName() {
		return taskTrigger.getName();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTask()
	 */
	public Object getTask() {
		return taskTrigger.getTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTriggerId()
	 */
	public Long getTriggerId() {
		return taskTrigger.getTriggerId();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuted()
	 */
	public boolean isExecuted() {
		return taskTrigger.isExecuted();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuting()
	 */
	public boolean isExecuting() {
		return taskTrigger.isExecuting();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isReScheduleTask()
	 */
	public boolean isReScheduleTask() {
		return taskTrigger.isReScheduleTask();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isShutdownTask()
	 */
	public boolean isShutdownTask() {
		return taskTrigger.isShutdownTask();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description) {
		taskTrigger.setDescription(description);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuted(boolean)
	 */
	public void setExecuted(boolean executed) {
		taskTrigger.setExecuted(executed);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuting(boolean)
	 */
	public void setExecuting(boolean executing) {
		taskTrigger.setExecuting(executing);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setName(java.lang.String)
	 */
	public void setName(String name) {
		taskTrigger.setName(name);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setReScheduleTask(boolean)
	 */
	public void setReScheduleTask(boolean reScheduleTask) {
		taskTrigger.setReScheduleTask(reScheduleTask);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setShutdownTask(boolean)
	 */
	public void setShutdownTask(boolean shutdownTask) {
		taskTrigger.setShutdownTask(shutdownTask);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setTask(java.lang.Object)
	 */
	public void setTask(Object task) {
		taskTrigger.setTask(task);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setTriggerId(java.lang.Long)
	 */
	public void setTriggerId(Long id) {
		taskTrigger.setTriggerId(id);
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isForceUnScheduleTask()
	 */
	public boolean isForceUnScheduleTask() {
		return taskTrigger.isForceUnScheduleTask();
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setForceUnScheduleTask(boolean)
	 */
	public void setForceUnScheduleTask(boolean forceUnScheduleTask) {
		taskTrigger.setForceUnScheduleTask(forceUnScheduleTask);
	}
}

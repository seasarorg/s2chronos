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
import org.seasar.chronos.core.util.ObjectUtil;

/**
 * 抽象トリガークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public abstract class AbstractTrigger implements TaskTrigger {
	private Long triggerId;

	private String name;

	private Object task;

	private String description;

	private boolean executed;

	private boolean executing;

	private boolean reScheduleTask;

	private boolean forceUnScheduleTask;

	private boolean shutdownTask;

	/**
	 * デフォルトコンストラクタです．
	 */
	public AbstractTrigger() {
	}

	/**
	 * コンストラクタです．
	 * 
	 * @param name
	 *            トリガー名
	 */
	public AbstractTrigger(String name) {
		this.setName(name);
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/*
	 * (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTask()
	 */
	public Object getTask() {
		return task;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#getTriggerId()
	 */
	public Long getTriggerId() {
		if (this.triggerId == null) {
			this.triggerId = ObjectUtil.generateObjectId();
		}
		return this.triggerId;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuting()
	 */
	public boolean isExecuting() {
		return this.executing;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setDescription(java.lang.String
	 * )
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuting(boolean)
	 */
	public void setExecuting(boolean executing) {
		this.executing = executing;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setTask(java.lang.Object)
	 */
	public void setTask(Object task) {
		this.task = task;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setTriggerId(java.lang.Long)
	 */
	public void setTriggerId(Long id) {
		this.triggerId = id;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isReScheduleTask()
	 */
	public boolean isReScheduleTask() {
		return reScheduleTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setReScheduleTask(boolean)
	 */
	public void setReScheduleTask(boolean reScheduleTask) {
		this.reScheduleTask = reScheduleTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isShutdownTask()
	 */
	public boolean isShutdownTask() {
		return shutdownTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setShutdownTask(boolean)
	 */
	public void setShutdownTask(boolean shutdownTask) {
		this.shutdownTask = shutdownTask;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isExecuted()
	 */
	public boolean isExecuted() {
		return executed;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setExecuted(boolean)
	 */
	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isForceUnScheduleTask()
	 */
	public boolean isForceUnScheduleTask() {
		return forceUnScheduleTask;
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.TaskTrigger#setForceUnScheduleTask(boolean)
	 */
	public void setForceUnScheduleTask(boolean forceUnScheduleTask) {
		this.forceUnScheduleTask = forceUnScheduleTask;
	}
}

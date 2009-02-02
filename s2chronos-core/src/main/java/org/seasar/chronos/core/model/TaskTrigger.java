/*
 * Copyright 2007-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.chronos.core.model;

import java.io.Serializable;

/**
 * 
 * @author j5ik2o
 * 
 */
public interface TaskTrigger extends Serializable {

	public String getDescription();

	public String getName();

	public Object getTask();

	public Long getTriggerId();

	public boolean isEndTask();

	public boolean isExecuted();

	public boolean isExecuting();

	public boolean isForceUnScheduleTask();

	public boolean isReScheduleTask();

	public boolean isShutdownTask();

	public boolean isStartTask();

	public void setDescription(String description);

	public void setEndTask(boolean endTask);

	public void setExecuted(boolean executed);

	public void setExecuting(boolean executing);

	public void setForceUnScheduleTask(boolean forceUnScheduleTask);

	public void setName(String name);

	public void setReScheduleTask(boolean reScheduleTask);

	public void setShutdownTask(boolean shutdownTask);

	public void setStartTask(boolean startTask);

	public void setTask(Object task);

	public void setTriggerId(Long id);
}
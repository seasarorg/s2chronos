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
package org.seasar.chronos.core;

import java.io.Serializable;

/**
 * 
 * @author j5ik2o
 * 
 */
public interface TaskTrigger extends Serializable {

	public String getDescription();

	public boolean isEndTask();

	public Long getTriggerId();

	public String getName();

	public boolean isStartTask();

	public Object getTask();

	public boolean isExecuted();

	public boolean isExecuting();

	public void setDescription(String description);

	public void setEndTask(boolean endTask);

	public void setExecuting(boolean executing);

	public void setExecuted(boolean executed);

	public void setTriggerId(Long id);

	public void setName(String name);

	public void setStartTask(boolean startTask);

	public void setTask(Object task);

	public boolean isReScheduleTask();

	public void setReScheduleTask(boolean reScheduleTask);

	public void setShutdownTask(boolean shutdownTask);

	public boolean isShutdownTask();
}
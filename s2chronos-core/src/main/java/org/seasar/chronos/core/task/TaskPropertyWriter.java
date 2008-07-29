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
package org.seasar.chronos.core.task;

import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;
import org.seasar.framework.beans.BeanDesc;

public interface TaskPropertyWriter {

	public Object getTask();

	public Class<?> getTaskClass();

	public BeanDesc getBeanDesc();

	public boolean hasTaskId();

	public boolean hasTaskName();

	public boolean hasDescription();

	public boolean hasStartTask();

	public boolean hasEndTask();

	public boolean hasShutdownTask();

	public boolean hasExecuted();

	public boolean hasReSchedule();

	public boolean hasTrigger();

	public boolean hasThreadPool();

	public boolean hasThreadPoolSize();

	public boolean hasThreadPoolType();

	public boolean hasException();

	public void setTaskId(Long value);

	public void setTaskName(String value);

	public void setDescription(String value);

	public void setStartTask(boolean value);

	public void setEndTask(boolean value);

	public void setShutdownTask(boolean value);

	public void setExecuted(boolean value);

	public void setReSchedule(boolean value);

	public void setTrigger(TaskTrigger value);

	public void setThreadPool(TaskThreadPool value);

	public void setThreadPoolSize(int value);

	public void setThreadPoolType(ThreadPoolType value);

	public void setup(Object task, Class<?> taskClass);

	public void setup(Object task, BeanDesc beanDesc);

	public void setException(Exception exception);

}

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

public interface TaskPropertyReader {

	public Object getTask();

	public Class<?> getTaskClass();

	public BeanDesc getBeanDesc();

	public void setup(Object task, Class<?> taskClass);

	public void setup(Object task, BeanDesc beanDesc);

	public boolean hasTaskId();

	public boolean hasTaskName();

	public boolean hasDescription();

	public boolean hasStartTask();

	public boolean hasEndTask();

	public boolean hasShutdownTask();

	public boolean hasExecuting();

	public boolean hasExecuted();

	public boolean hasReScheduleTask();

	public boolean hasTrigger();

	public boolean hasThreadPool();

	public boolean hasThreadPoolSize();

	public boolean hasThreadPoolType();

	public boolean hasException();

	public Long getTaskId(Long defaultValue);

	public String getTaskName(String defaultValue);

	public String getDescription(String defaultValue);

	public boolean isStartTask(boolean defaultValue);

	public boolean isEndTask(boolean defaultValue);

	public boolean isShutdownTask(boolean defaultValue);

	public boolean isExecuting(boolean defaultValue);

	public boolean isExecuted(boolean defaultValue);

	public boolean isReScheduleTask(boolean defaultValue);

	public TaskTrigger getTrigger(TaskTrigger defaultValue);

	public TaskThreadPool getThreadPool(TaskThreadPool defaultValue);

	public int getThreadPoolSize(int defaultValue);

	public ThreadPoolType getThreadPoolType(ThreadPoolType defaultValue);

	public Exception getException(Exception exception);

}

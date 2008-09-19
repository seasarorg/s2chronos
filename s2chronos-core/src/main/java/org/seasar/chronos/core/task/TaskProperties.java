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

import org.seasar.chronos.core.Scheduler;
import org.seasar.chronos.core.TaskThreadPool;
import org.seasar.chronos.core.TaskTrigger;
import org.seasar.chronos.core.ThreadPoolType;

public interface TaskProperties {

	public String getDescription();

	public Scheduler getScheduler();

	public Object getTask();

	public Class<?> getTaskClass();

	public long getTaskId();

	public String getTaskName();

	public TaskThreadPool getThreadPool();

	public int getThreadPoolSize();

	public ThreadPoolType getThreadPoolType();

	public TaskTrigger getTrigger();

	public boolean isEndTask();

	public boolean isExecuting();

	public boolean isExecuted();

	public boolean isPrepared();

	public boolean isReScheduleTask();

	public boolean isShutdownTask();

	public boolean isStartTask();

	public boolean isForceUnScheduleTask();

	public void setEndTask(boolean endTask);

	public void setExecuting(boolean executing);

	public void setExecuted(boolean executed);

	public void setGetterSignal(Object getterSignal);

	public void setScheduler(Scheduler scheduler);

	public void setShutdownTask(boolean shutdownTask);

	public void setStartTask(boolean startTask);

	public void setTask(Object task);

	public void setTaskClass(Class<?> taskClass);

	public void setTaskId(long taskId);

	public void setThreadPool(TaskThreadPool taskThreadPool);

	public void setTrigger(TaskTrigger taskTrigger);

	public void setException(Exception exception);

	public void setForceUnScheduleTask(boolean forceUnScheduleTask);

	public Exception getException();

	public void setHotdeployDisable(boolean hotdeployDisable);

	public boolean isHotdeployDisable();
}

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

import java.util.Date;
import java.util.concurrent.Future;

import org.seasar.chronos.core.impl.TaskStateType;
import org.seasar.chronos.core.task.TaskExecutorService;
import org.seasar.framework.container.ComponentDef;

public interface TaskScheduleEntry extends Serializable {

	public ComponentDef getComponentDef();

	public Long getScheduleId();

	public Object getTask();

	public Class<?> getTaskClass();

	public TaskExecutorService getTaskExecutorService();

	public Future<TaskExecutorService> getTaskStaterFuture();

	public TaskStateType getTaskStateType();

	public void setComponentDef(ComponentDef componentDef);

	public void setScheduleId(Long scheduleId);

	public void setTask(Object target);

	public void setTaskClass(Class<?> targetClass);

	public void setTaskExecutorService(TaskExecutorService taskExecutorService);

	public void setTaskStaterFuture(Future<TaskExecutorService> future);

	public void setTaskStateType(TaskStateType taskStateType);

	public Date getUnScheduledDate();

	public void setUnScheduledDate(Date date);

}

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

import org.seasar.chronos.core.impl.TaskStateType;

/**
 * スケジューライベントリスナークラスです．
 * 
 * @author j5ik2o
 * 
 */
public interface SchedulerEventListener {

	public void addTaskScheduleEntry(Scheduler scheduler,
			TaskStateType taskStateType, TaskScheduleEntry taskScheduleEntry);

	public void cancelTask(Scheduler scheduler, Object task);

	public void endScheduler(Scheduler scheduler);

	public void endTask(Scheduler scheduler, Object task);

	public void pauseScheduler(Scheduler scheduler);

	public void resumeScheduler(Scheduler scheduler);

	public void removeTaskScheduleEntry(Scheduler scheduler,
			TaskStateType taskStateType, TaskScheduleEntry taskScheduleEntry);

	public void resigtTaskAfterScheduler(Scheduler scheduler);

	public void resigtTaskBeforeScheduler(Scheduler scheduler);

	public void shutdownScheduler(Scheduler scheduler);

	public void startScheduler(Scheduler scheduler);

	public void startTask(Scheduler scheduler, Object task);

	public void exceptionTask(Scheduler scheduler, Object task, Exception e);

}

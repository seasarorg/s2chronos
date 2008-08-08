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

/**
 * スケジューラ設定クラスです．
 * 
 * @author j5ik2o
 * 
 */
public final class SchedulerConfiguration {

	private boolean daemon;

	private boolean autoFinish;

	private long autoFinishTimeLimit;

	private long taskScanIntervalTime;

	private int threadPoolSize;

	private ThreadPoolType threadPoolType;

	private long taskStateCleanupTime;
	
	private boolean hotdeployDisabled;
	
	public boolean isAutoFinish() {
		return autoFinish;
	}

	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

	public long getAutoFinishTimeLimit() {
		return autoFinishTimeLimit;
	}

	public void setAutoFinishTimeLimit(long zeroScheduleTime) {
		this.autoFinishTimeLimit = zeroScheduleTime;
	}

	public long getTaskScanIntervalTime() {
		return taskScanIntervalTime;
	}

	public void setTaskScanIntervalTime(long taskScanIntervalTime) {
		this.taskScanIntervalTime = taskScanIntervalTime;
	}

	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	public ThreadPoolType getThreadPoolType() {
		return this.threadPoolType;
	}

	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	/**
	 * @return the daemon
	 */
	public boolean isDaemon() {
		return daemon;
	}

	/**
	 * @param daemon
	 *            the daemon to set
	 */
	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	public long getTaskStateCleanupTime() {
		return taskStateCleanupTime;
	}

	public void setTaskStateCleanupTime(long taskStateCleanupTime) {
		this.taskStateCleanupTime = taskStateCleanupTime;
	}

	public boolean isHotdeployDisabled() {
		return hotdeployDisabled;
	}

	public void setHotdeployDisabled(boolean hotdeployEnabled) {
		this.hotdeployDisabled = hotdeployEnabled;
	}
}

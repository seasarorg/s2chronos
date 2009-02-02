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


/**
 * スケジューラ設定クラスです．
 * 
 * @author j5ik2o
 */
public final class SchedulerConfiguration {
	private boolean daemon;

	private boolean autoFinish;

	private long autoFinishTimeLimit;

	private long taskScanIntervalTime;

	private int threadPoolSize;

	private ThreadPoolType threadPoolType;

	private long taskStateCleanupTime;

	private boolean hotdeployDisable;

	/**
	 * autoFinishを取得します。
	 * <p>
	 * </p>
	 * 
	 * @return autoFinish
	 */
	public boolean isAutoFinish() {
		return autoFinish;
	}

	/**
	 * autoFinishを設定します。
	 * 
	 * @param autoFinish
	 *            autoFinish
	 */
	public void setAutoFinish(boolean autoFinish) {
		this.autoFinish = autoFinish;
	}

	/**
	 * autoFinishTimeLimitを取得します。
	 * 
	 * @return autoFinishTimeLimit
	 */
	public long getAutoFinishTimeLimit() {
		return autoFinishTimeLimit;
	}

	/**
	 * autoFinishTimeLimitを設定します。
	 * 
	 * @param autoFinishTimeLimit
	 *            autoFinishTimeLimit
	 */
	public void setAutoFinishTimeLimit(long autoFinishTimeLimit) {
		this.autoFinishTimeLimit = autoFinishTimeLimit;
	}

	/**
	 * taskScanIntervalTimeを取得します。
	 * 
	 * @return taskScanIntervalTime
	 */
	public long getTaskScanIntervalTime() {
		return taskScanIntervalTime;
	}

	/**
	 * taskScanIntervalTimeを設定します。
	 * 
	 * @param taskScanIntervalTime
	 *            taskScanIntervalTime
	 */
	public void setTaskScanIntervalTime(long taskScanIntervalTime) {
		this.taskScanIntervalTime = taskScanIntervalTime;
	}

	/**
	 * threadPoolSizeを取得します。
	 * 
	 * @return threadPoolSize
	 */
	public int getThreadPoolSize() {
		return threadPoolSize;
	}

	/**
	 * threadPoolTypeを取得します。
	 * 
	 * @return threadPoolType
	 */
	public ThreadPoolType getThreadPoolType() {
		return this.threadPoolType;
	}

	/**
	 * threadPoolSizeを設定します。
	 * 
	 * @param threadPoolSize
	 *            threadPoolSize
	 */
	public void setThreadPoolSize(int threadPoolSize) {
		this.threadPoolSize = threadPoolSize;
	}

	/**
	 * threadPoolTypeを設定します。
	 * 
	 * @param threadPoolType
	 *            threadPoolType
	 */
	public void setThreadPoolType(ThreadPoolType threadPoolType) {
		this.threadPoolType = threadPoolType;
	}

	/**
	 * daemonを取得します。
	 * 
	 * @return daemon
	 */
	public boolean isDaemon() {
		return daemon;
	}

	/**
	 * daemonを設定します。
	 * 
	 * @param daemon
	 *            daemon
	 */
	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	/**
	 * taskStateCleanupTimeを取得します。
	 * 
	 * @return taskStateCleanupTime
	 */
	public long getTaskStateCleanupTime() {
		return taskStateCleanupTime;
	}

	/**
	 * taskStateCleanupTimeを設定します。
	 * 
	 * @param taskStateCleanupTime
	 *            taskStateCleanupTime
	 */
	public void setTaskStateCleanupTime(long taskStateCleanupTime) {
		this.taskStateCleanupTime = taskStateCleanupTime;
	}

	/**
	 * hotdeployDisableを取得します。
	 * 
	 * @return hotdeployDisable
	 */
	public boolean isHotdeployDisable() {
		return hotdeployDisable;
	}

	/**
	 * hotdeployDisableを設定します。
	 * 
	 * @param hotdeployDisable
	 *            hotdeployDisable
	 */
	public void setHotdeployDisable(boolean hotdeployDisable) {
		this.hotdeployDisable = hotdeployDisable;
	}
}

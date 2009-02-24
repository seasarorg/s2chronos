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

/**
 * 遅延トリガークラスです．
 * 
 * @author j5ik2o
 */
@SuppressWarnings("serial")
public class CDelayTrigger extends AbstractTrigger {
	private long delay = 0;

	private long triggerTime = 0;

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.trigger.AbstractTrigger#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * デフォルトコンストラクタです．
	 */
	public CDelayTrigger() {
		super("delayTrigger");
	}

	/**
	 * コンストラクタです．
	 * 
	 * @param delay
	 *            遅延秒数
	 */
	public CDelayTrigger(long delay) {
		this();
		this.setDelay(delay);
	}

	/*
	 * (非 Javadoc)
	 * @see
	 * org.seasar.chronos.core.model.trigger.AbstractTrigger#isReScheduleTask()
	 */
	@Override
	public boolean isReScheduleTask() {
		return false;
	}

	/**
	 * 遅延時間を設定します．
	 * 
	 * @param delay
	 *            遅延時間(msec)
	 */
	public void setDelay(long delay) {
		this.delay = delay;
		calculateTime();
	}

	/**
	 * 遅延実行時間を計算します．
	 */
	public void calculateTime() {
		triggerTime = System.currentTimeMillis() + delay;
	}

	/**
	 * 遅延時間を返します．
	 * 
	 * @return 遅延時間(msec)
	 */
	public long getDelay() {
		return delay;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
	public boolean isStartTask() {
		if (this.isExecuting()) {
			return false;
		}
		boolean startTimeCheck = false;
		// 開始時刻の確認
		startTimeCheck = (System.currentTimeMillis() >= triggerTime);
		return startTimeCheck;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isEndTask()
	 */
	public boolean isEndTask() {
		return false;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setStartTask(boolean)
	 */
	public void setStartTask(boolean startTask) {
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#setEndTask(boolean)
	 */
	public void setEndTask(boolean endTask) {
	}
}

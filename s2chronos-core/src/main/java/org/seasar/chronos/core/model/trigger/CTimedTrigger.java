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

import java.util.Date;

/**
 * @author kato
 */
@SuppressWarnings("serial")
public class CTimedTrigger extends AbstractTrigger {
	private Date startTime;

	private Date endTime;

	/**
	 * コンストラクタです．
	 */
	public CTimedTrigger() {
		super("timedTrigger");
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
	 * 開始日時を設定します．
	 * 
	 * @param startDate
	 *            開始日時
	 */
	public void setStartTime(Date startDate) {
		this.startTime = (Date) startDate.clone();
	}

	/**
	 * 開始日時を返します．
	 * 
	 * @return 開始日時
	 */
	public Date getStartTime() {
		return (Date) startTime.clone();
	}

	/**
	 * 終了日時を設定します．
	 * 
	 * @param endDate
	 *            終了日時
	 */
	public void setEndTime(Date endDate) {
		this.endTime = (Date) endDate.clone();
	}

	/**
	 * 終了日時を返します．
	 * 
	 * @return 終了日時
	 */
	public Date getEndTime() {
		return (Date) endTime.clone();
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isStartTask()
	 */
	public boolean isStartTask() {
		if (this.isExecuting() || this.isExecuted()) {
			return false;
		}
		boolean startTimeCheck = false;
		// 開始時刻の確認
		if (startTime != null) {
			startTimeCheck =
			    (System.currentTimeMillis() >= startTime.getTime());
		}
		return startTimeCheck;
	}

	/*
	 * (非 Javadoc)
	 * @see org.seasar.chronos.core.model.TaskTrigger#isEndTask()
	 */
	public boolean isEndTask() {
		boolean endTimeCheck = false;
		// 終了時刻の確認
		if (endTime != null) {
			endTimeCheck = (System.currentTimeMillis() >= endTime.getTime());
		}
		return endTimeCheck;
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
